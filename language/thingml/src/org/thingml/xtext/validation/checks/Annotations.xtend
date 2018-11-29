package org.thingml.xtext.validation.checks

import org.eclipse.xtext.validation.Check
import org.thingml.annotations.AnnotationRegistry
import org.thingml.xtext.thingML.AnnotatedElement
import org.thingml.xtext.thingML.PlatformAnnotation
import org.thingml.xtext.thingML.ThingMLPackage
import org.thingml.xtext.validation.ThingMLValidatorCheck

class Annotations extends ThingMLValidatorCheck {
	
	@Check(FAST)
	def checkAnnotation(PlatformAnnotation a) {
		val source = a.eContainer as AnnotatedElement
		if (!AnnotationRegistry.annotations.containsKey(a.name)) {
			val msg = "Annotation @" + a.name + " is not registered. Non-registered annotations will be deprecated in a future version."
			warning(msg, source, ThingMLPackage.eINSTANCE.annotatedElement_Annotations, source.annotations.indexOf(a), "unknown-annotation")
			return
		}
		
		val reg_a = AnnotationRegistry.annotations.get(a.name)
		if (!reg_a.check(source, a.value)) {
			val msg = "Annotation @" + a.name + " is invalid. " + reg_a
			warning(msg, source, ThingMLPackage.eINSTANCE.annotatedElement_Annotations, source.annotations.indexOf(a), "invalid-annotation")
			return
		}		
	}
}