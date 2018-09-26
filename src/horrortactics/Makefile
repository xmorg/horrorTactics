JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Actor.java \
	ActorMap.java \
	Cutscene.java \
	KeyActions.java \
	MouseActions.java \
	MyTiledMap.java \
	SaveMyFile.java \
	TitleMenu.java \
	Trigger.java \
	HorrorTactics.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
