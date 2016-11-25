#/bin/bash
cp dist/horrorTactics.jar ./
java -Djava.library.path=/home/tcooper/devel/slick -jar horrorTactics.jar -cp /home/tcooper/devel/slick horrortactics.HorrorTactics
