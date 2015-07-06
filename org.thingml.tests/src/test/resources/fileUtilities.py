
def insertLine(insert,path,match):
	f = open(path, "r")
	contents=[]
	for line in f:
		contents.append(line)
		if contents[-1].startswith(match):
			contents.append(insert+"\n")
	f.close()
	f = open(path, "w")
	contents = "".join(contents)
	f.write(contents)
	f.close()
def insertLineBefore(insert,path,match):
	f = open(path, "r")
	contents=[]
	inserted = False
	for line in f:
		if line.startswith(match) and not inserted:
			contents.append(insert+"\n")
			inserted = True
		contents.append(line)
	f.close()
	f = open(path, "w")
	contents = "".join(contents)
	f.write(contents)
	f.close()
def replaceLine(insert,path,match):
	f = open(path, "r")
	contents=[]
	for line in f:
		if line.startswith(match):
			contents.append(insert+"\n")
		else:
			contents.append(line)
	f.close()
	f = open(path, "w")
	contents = "".join(contents)
	f.write(contents)
	f.close()
def find(match,path):
	f = open(path, "r")
	for line in f:
		if line.startswith(match):
			return line
	return None
def findAfter(match,path):
	f = open(path, "r")
	found = False
	for line in f:
		if found:
			return line
		if line.startswith(match):
			found = True
	return None