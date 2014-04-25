import random
import sys
class Element:
	def __init__(self):
		self.ID=None
class Content:
	def __init__(self):
		self.inputs=[]
		self.outputs = []
		self.outputsNumber = 0
		self.final=False
		self.parent=None
		Element.__init__(self)
	def accept(self,visitor):
		visitor.process(self)
	def getReachables(self,list):
		for o in self.outputs:
			if not o in list:
				list.append(o)
				list=o.getReachables(list)
		return list
	def getNonBlockers(self,list):
		for i in self.inputs:
			if not i in list:
				list.append(i)
				list=i.getNonBlockers(list)
		return list
		
class Group:
	def __init__(self):
		self.content = []
		self.init = None
		self.finalStates = []
		Element.__init__(self)
	def accept(self,visitor):
		visitor.process(self)
		for e in self.content:
			e.accept(visitor)
		visitor.finalize(self)
		
class Region(Group):
	def __init__(self):
		Group.__init__(self)
		self.isGroup=True
		self.isContent=False
	def dump(self):
		finals=""
		for e in self.finalStates:
			finals=finals+str(e.ID)+" "
		print("region "+str(self.ID)+" init "+str(self.init.ID)+" finals: "+finals)
		for e in self.content:
			e.dump()
class Composite(Group,Content):
	def __init__(self):
		Group.__init__(self)
		Content.__init__(self)
		self.isGroup=True
		self.isContent=True
	def dump(self):
		outputs=""
		finals=""
		for e in self.outputs:
			outputs=outputs+str(e.ID)+" "
		for e in self.finalStates:
			finals=finals+str(e.ID)+" "
		print("composite "+str(self.ID)+" to "+outputs+" init "+str(self.init.ID)+" finals: "+finals)
		for e in self.content:
			e.dump()
		print("end of composite")
		
class State(Content):
	def __init__(self):
		Content.__init__(self)
		self.isGroup=False
		self.isContent=True
	def dump(self):
		outputs=""
		for e in self.outputs:
			outputs=outputs+str(e.ID)+" "
		print("state "+str(self.ID)+" to "+outputs)

class Configuration:
	def __init__(self):#,depth,compositeRatio,):
		self.minRegion=None
		self.maxRegion=None
		self.minState=None
		self.maxState=None
		self.minOutputs=None
		self.maxOutputs=None
		self.depth=None
		self.compositeRatio=None
	def setRegions(self,minRegion,maxRegion):
		self.minRegion=minRegion
		self.maxRegion=maxRegion
	def setStates(self,minState,maxState):
		self.minState=minState
		self.maxState=maxState
	def setOutputs(self,minOutputs,maxOutputs):
		self.minOutputs=minOutputs
		self.maxOutputs=maxOutputs
	def setDepth(self,depth):
		self.depth=depth
	def setCompositeRatio(self,compositeRatio):
		self.compositeRatio=compositeRatio
	def isValid(self):
		if(self.minRegion is None or self.maxRegion is None or self.minState is None or self.maxState is None or self.minOutputs is None or self.maxOutputs is None or self.depth is None or self.compositeRatio is None):
			return False
		if(self.minRegion<1 or self.minState<1 or self.minOutputs<1 or self.depth<1 or self.minRegion>self.maxRegion or self.minState>self.maxState or self.minOutputs>self.maxOutputs or self.compositeRatio<0 or self.compositeRatio>1):
			return False
		return True
class Initializer:
	def __init__(self,conf):
		self.regions = []
		self.conf = conf
		self.currentID=0
		if conf.isValid():
			print("valid configuration!")
			for _ in range(random.randint(self.conf.minRegion,self.conf.maxRegion)):
				self.regions.append(Region())
			for r in self.regions:
				r.ID=self.currentID
				self.currentID=self.currentID+1
				r.accept(self)
			OutGenerator(conf,self.regions)
		else:
			print("invalid configuration!")
	def process(self,element):
		if element.isGroup:
			self.conf.depth = self.conf.depth-1
			for _ in range(random.randint(self.conf.minState,self.conf.maxState)):
				if self.conf.depth>0 and random.random()<self.conf.compositeRatio:
					element.content.append(Composite())
				else:
					element.content.append(State())
				element.content[-1].ID=self.currentID
				self.currentID=self.currentID+1
	def finalize(self,element):
		self.conf.depth = self.conf.depth+1
		
# """
class OutGenerator:
	def __init__(self,conf,regions):
		self.conf = conf
		self.regions = regions
		for r in self.regions:
			r.accept(self)
		TransitionGenerator(conf,regions)
	def process(self,element):
		if element.isGroup:
			self.conf.depth = self.conf.depth-1
			element.init = element.content[random.randint(0,len(element.content)-1)]
			for e in element.content:
				e.outputsNumber=random.randint(self.conf.minOutputs,self.conf.maxOutputs)
				e.parent=element
			if element.isContent:#thus is a composite
				for _ in range(0,element.outputsNumber):
					element.finalStates.append(element.content[random.randint(0,len(element.content)-1)])
					element.finalStates[-1].final=True
			else:#region
				element.finalStates.append(element.init)
				element.finalStates[-1].final=True
	def finalize(self,element):
		self.conf.depth = self.conf.depth+1
# """
class TransitionGenerator:
	def __init__(self,conf,regions):
		self.conf = conf
		self.regions = regions
		for r in self.regions:
			r.accept(self)
		TransitionSolver(conf,regions)
	def process(self,element):
		if element.isGroup:
			self.conf.depth = self.conf.depth-1
			for e in element.content:
				for _ in range(e.outputsNumber):
					newOutput=element.content[random.randint(0,len(element.content)-1)]
					e.outputs.append(newOutput)
					newOutput.inputs.append(e)
	def finalize(self,element):
		self.conf.depth = self.conf.depth+1
# """
class TransitionSolver:
	def __init__(self,conf,regions):
		self.regions = regions
		self.conf = conf
		for r in self.regions:
			r.accept(self)
	def process(self,element):
		if element.isGroup:
			self.conf.depth = self.conf.depth-1
			blockers=self.getBlockers(element)
			nonReachables=self.getNonReachables(element)
			while(len(blockers)>0 or len(nonReachables)>0):
				if(random.random()>0.5):
					if(len(blockers)>0):
						blocker = blockers[random.randint(0,len(blockers)-1)]
						oldOutput=blocker.outputs[random.randint(0,len(blocker.outputs)-1)]
						newOutput=element.content[random.randint(0,len(element.content)-1)]
						
						blocker.outputs.remove(oldOutput)
						oldOutput.inputs.remove(blocker)
						
						blocker.outputs.append(newOutput)
						newOutput.inputs.append(blocker)
						
				else:
					if(len(nonReachables)>0):
						reachables=element.init.getReachables([])
						switch = reachables[random.randint(0,len(reachables)-1)]
						oldOutput=switch.outputs[random.randint(0,len(switch.outputs)-1)]
						newOutput=nonReachables[random.randint(0,len(nonReachables)-1)]
						
						switch.outputs.remove(oldOutput)
						oldOutput.inputs.remove(switch)
						
						switch.outputs.append(newOutput)
						newOutput.inputs.append(switch)
				blockers=self.getBlockers(element)
				nonReachables=self.getNonReachables(element)
	def finalize(self,element):
		self.conf.depth = self.conf.depth+1
	def getBlockers(self,element):
		nonBlockers=[]
		for e in element.finalStates:
			for nonBlocker in e.getNonBlockers([e]):
				if nonBlocker not in nonBlockers:
					nonBlockers.append(nonBlocker)
		blockers=[]
		for e in element.content:
			if not e in nonBlockers:
				blockers.append(e)
		"""Debug dump
		print("\n\n\n")
		for r in self.regions:
			r.dump()
		print("blockers:")
		for e in blockers:
			print(str(e.ID))
		print("nonBlockers:")
		for e in nonBlockers:
			print(str(e.ID))#"""
		return blockers
	def getNonReachables(self,element):
		# return []
		reachables=element.init.getReachables([element.init])
		nonReachables=[]
		for e in element.content:
			if not e in reachables:
				nonReachables.append(e)
		"""Debug dump
		print("\n\n\n")
		for r in self.regions:
			r.dump()
		print("nonreachables:")
		for e in nonReachables:
			print(str(e.ID))
		print("reachables:")
		for e in reachables:
			print(str(e.ID))#"""
		return nonReachables
# """
class DumpThingml:
	def __init__(self,regions):
		self.depth = 1
		self.regions=regions
		self.file=open("bigTestExample.thingml",'w')
		self.file.write("/** Abstract tree:\n")
		oldstdout=sys.stdout
		sys.stdout=self.file
		for r in tree:
			r.dump()
		sys.stdout=oldstdout
			
		self.file.write("*/\n\n\n")
		
		self.file.write("import \"../../../../../org.thingml.samples/src/main/thingml/thingml.thingml\"\n\n\
thing BigTestExample includes Test\n\
@test \" # \"\n\
{\n\
statechart BigTestExample init s"+str(self.regions[0].init.ID)+"{\n")
		self.firstRegion=True
		for r in self.regions:
			r.accept(self)
		self.file.write("}//end of statechart\n")
		self.file.write("}//end of thing\n")
	def process(self,element):
		if element.isGroup and not element.isContent:#region
			if self.firstRegion:
				self.file.write("\t/** First region is implicit\n\tregion s"+str(element.ID)+" init s"+str(element.init.ID)+" {*/\n")
			else:
				self.file.write("\tregion s"+str(element.ID)+" init s"+str(element.init.ID)+" {\n")
			self.depth=self.depth+1
		elif element.isGroup:#composite
			tabs=""
			for _ in range(0,self.depth):
				tabs=tabs+"\t"
			self.file.write(tabs+"composite state s"+str(element.ID)+" init s"+str(element.init.ID)+" {\n")
			tabs=tabs+"\t"
			self.file.write(tabs+"on entry harness!testOut('\\'"+str(element.ID)+"\\'')\n")
			i=0
			if element.parent.isContent: # Composite parent
				outputsNumber=element.outputsNumber+element.parent.finalStates.count(element)
			else: # Region parent
				outputsNumber=element.outputsNumber
			for e in element.outputs:
				self.file.write(tabs+"transition -> s"+str(e.ID)+"\n")
				self.file.write(tabs+"event m : harness?bigTestIn\n")
				self.file.write(tabs+"guard m.i%"+str(element.outputsNumber)+" == "+str(i)+"\n")
				self.file.write(tabs+"\n")
				i=i+1
			if element.parent.isContent and element.parent.finalStates.count(element)>0: # Composite parent
				self.file.write(tabs+"transition -> final_"+str(element.parent.ID)+"\n")
				self.file.write(tabs+"event m : harness?bigTestIn\n")
				self.file.write(tabs+"guard m.i%"+str(outputsNumber)+" > "+str(i-1)+"\n")
			
			self.file.write(tabs+"state final_"+str(element.ID)+" {}\n\n")
			self.depth=self.depth+1
		else:#State
			outertabs=""
			for _ in range(0,self.depth):
				outertabs=outertabs+"\t"
			self.file.write(outertabs+"state s"+str(element.ID)+" {\n")
			tabs=outertabs+"\t"
			self.file.write(tabs+"on entry harness!testOut('\\'"+str(element.ID)+"\\'')\n\n")
			i=0
			if element.parent.isContent: # Composite parent
				outputsNumber=element.outputsNumber+element.parent.finalStates.count(element)
			else: # Region parent
				outputsNumber=element.outputsNumber
			for e in element.outputs:
				self.file.write(tabs+"transition -> s"+str(e.ID)+"\n")
				self.file.write(tabs+"event m : harness?bigTestIn\n")
				self.file.write(tabs+"guard m.i%"+str(outputsNumber)+" == "+str(i)+"\n")
				if e != element.outputs[-1]:
					self.file.write(tabs+"\n")
				i=i+1
			if element.parent.isContent and element.parent.finalStates.count(element)>0: # Composite parent
				self.file.write("\n"+tabs+"transition -> final_"+str(element.parent.ID)+"\n")
				self.file.write(tabs+"event m : harness?bigTestIn\n")
				self.file.write(tabs+"guard m.i%"+str(outputsNumber)+" > "+str(i-1)+"\n")
			
			self.file.write(outertabs+"}\n\n")
	def finalize(self,element):
		self.depth=self.depth-1
		tabs=""
		for _ in range(0,self.depth):
			tabs=tabs+"\t"
		if self.firstRegion and not element.isContent:
			self.file.write("\t//} End of first implicit region\n\n")
			self.firstRegion = False
		elif element.isContent: 
			self.file.write(tabs+"}//end of composite\n\n")
		else: 
			self.file.write(tabs+"}//end of region\n\n")
	
	
conf = Configuration()
conf.setRegions(1,1)
conf.setStates(2,2)
conf.setOutputs(1,2)
conf.setDepth(2)
conf.setCompositeRatio(0.5)

tree=Initializer(conf).regions
DumpThingml(tree)
print("\n\n\n")
for r in tree:
	r.dump()

# """