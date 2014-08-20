
def dumpHTML(path,values):
	f = open(path,'a')
	f.write("\n<tr><th></th><th></th><th></th><th></th><th></th></tr>\n")
	
	for v in values:
		compiler,name,cpu,mem,size,tcount,cputime=v
		f.write("<tr class=\"")
		# if cpu !="error":
			# f.write("green")
		#else:
			#f.write("red")
		f.write("\">\n\
<th>"+compiler+"</th><th>"+name+"</th><th>"+cpu+"</th><th>"+mem+"</th><th>"+size+"</th><th>"+tcount+"</th><th>"+cputime+"</th>\n\
</tr>")
# <tr>
# <tr class="green">
# <th>testHello</th><th>Scala</th><th>Success</th>
# </tr>
