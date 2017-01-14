#!/usr/bin/python

import os
import sys
import pwd
import ConfigParser
import subprocess
import StringIO
import shutil

CONFIG_NAME = 'config.ini'
CONFIG_GENERAL_SEC = 'general'

GENERAL_ROOT_TEST_FOLDER = 'root_test_folder'
GENERAL_TEST_WORKING_FOLDER = 'test_working_folder'
GENERAL_GLOBAL_REPORT_DIR = 'global_report_dir'


TEST_CATEGORY_NAME = 'category_name'
TEST_SCRIPT_NAME = 'category_test_script'

TEST_CATEGORY_REPORT_FOLDER = 'report'
TEST_CATEGORY_LOG = 'logs.log'
SCRIP_ABSOLUTE_PATH = os.path.dirname(os.path.realpath(__file__))


def print_std(category, stdout, stderr):
	for line in StringIO.StringIO(stdout).readlines():
		print "[STDOUT " + category + "] " + line.rstrip()

	for line in StringIO.StringIO(stderr).readlines():
		print "[STDERR " + category + "] " + line.rstrip()

def folder_path(parent, child):
	parent = parent.strip()
	child = child.strip()
	return os.path.join(parent, child)

def copy_dir_contents(src, dst, symlinks=False, ignore=None):
	for item in os.listdir(src):
		s = os.path.join(src, item)
		d = os.path.join(dst, item)
		if os.path.isdir(s):
 			shutil.copytree(s, d, symlinks, ignore)
		else:
			shutil.copy2(s, d)

def run_tests():
	root_test_folder = SCRIP_ABSOLUTE_PATH

	config = ConfigParser.RawConfigParser()
	config.read(os.path.join(root_test_folder, CONFIG_NAME))

	test_working_folder = config.get(CONFIG_GENERAL_SEC, GENERAL_TEST_WORKING_FOLDER)
	global_report_dir = config.get(CONFIG_GENERAL_SEC, GENERAL_GLOBAL_REPORT_DIR)

	full_test_working_folder = folder_path(root_test_folder, test_working_folder)
	full_global_report_dir = folder_path(root_test_folder, global_report_dir)

	master_slave_user = os.environ.get('MASTER_SLAVE_USER')
	master_slave_pwd = os.environ.get('MASTER_SLAVE_PWD')
	if not master_slave_user:
		message = "MASTER_SLAVE_USER env variable is not set!. Exiting..."
		sys.stderr.write(message + '\n');
		return

	if not master_slave_pwd:
		message = "MASTER_SLAVE_PWD env variable is not set!. Exiting..."
		sys.stderr.write(message + '\n');
		return

	#clean working directory and report directories
	if os.path.isdir(full_test_working_folder):
		shutil.rmtree(full_test_working_folder)
	os.mkdir(full_test_working_folder)

	if os.path.isdir(full_global_report_dir):
		shutil.rmtree(full_global_report_dir)
	os.mkdir(full_global_report_dir)


 	test_scripts = {}

 	#parsint config.ini and creating a dict with data to do testing
	for section in config.sections():
		if section == CONFIG_GENERAL_SEC:
			continue

		category_name = config.get(section, TEST_CATEGORY_NAME)
		test_category_working_folder = folder_path(full_test_working_folder, category_name)
		os.mkdir(test_category_working_folder)

		category_run_script = config.get(section, TEST_SCRIPT_NAME)
		run_script_folder = folder_path(root_test_folder, category_name)
		run_script_path = folder_path(run_script_folder, category_run_script)

		test_category_data = {}
		test_category_data['script'] = run_script_path
		test_category_data['working_folder'] = test_category_working_folder
		test_category_data['report_folder'] = folder_path(test_category_working_folder, TEST_CATEGORY_REPORT_FOLDER)
		test_scripts[category_name] = test_category_data
		os.mkdir(test_category_data['report_folder'])

	#running jobs as subprocesses
	for key, value in test_scripts.iteritems():
		command = [value['script'], key, value['working_folder'], value['report_folder'], 
			master_slave_user, master_slave_pwd, '22']
		print "Starting: " + " ".join(command)
		proc = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
		test_scripts[key]['proc'] = proc

	#waiting for all jobs to be completed and writing to log file
	for key, value in test_scripts.iteritems():
		stdout, stderr = value['proc'].communicate()
		print_std(key, stdout, stderr)
		log_file = folder_path(value['report_folder'], TEST_CATEGORY_LOG) 
		with open(log_file, 'w') as file:
			for line in StringIO.StringIO(stdout).readlines():
				file.write(line)
			for line in StringIO.StringIO(stderr).readlines():
				file.write(line)
		dest_folder = folder_path(full_global_report_dir, category_name)
		os.mkdir(dest_folder)
		shutil.copy2(log_file, os.path.join(dest_folder, TEST_CATEGORY_LOG))
		#copy final report to global report directory
		final_report_folder = folder_path(value['report_folder'], category_name)
		if os.path.isdir(final_report_folder): 
			copy_dir_contents(final_report_folder, dest_folder)

	body_contents, header_contents, litem_contents, footer_contents = "", "", "", ""
	header_location = folder_path(folder_path(root_test_folder, "html_report_templates"), "header.html")
	footer_location = folder_path(folder_path(root_test_folder, "html_report_templates"), "footer.html")
	litem_location = folder_path(folder_path(root_test_folder, "html_report_templates"), "listitem.html")

	with open(header_location, 'r') as file:
		header_contents = file.read()

	with open(footer_location, 'r') as file:
		footer_contents = file.read()

	with open(litem_location, 'r') as file:
		litem_contents = file.read()

	for key, value in test_scripts.iteritems():
		status = 'SUCCESS (LOG)'
		if value['proc'].returncode != 0:
			status = 'FAILURE (LOG)'

		item = litem_contents.replace('category_name_results', key)
		item = item.replace('category_link', folder_path(key, 'results.html'))
		item = item.replace('category_status', status)
		item = item.replace('category_logs_status', folder_path(key, TEST_CATEGORY_LOG))
		body_contents = body_contents + item

	final_report_contents = header_contents + body_contents + footer_contents
	with open(folder_path(full_global_report_dir, 'index.html'), 'w') as file:
		file.write(final_report_contents)

if __name__ == "__main__":
	run_tests()
	print "Done!"