#/bin/bash
cp dist/horrorTactics.jar ./
#cp natives/* dist/lib/
#java -Djava.library.path=/home/tcooper/devel/slick -jar horrorTactics.jar -cp /home/tcooper/devel/slick horrortactics.HorrorTactics
java -Djava.library.path=./lib -jar horrorTactics.jar -cp /home/tcooper/devel/slick horrortactics.HorrorTactics
