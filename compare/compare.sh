rm JFDF.jar
cp ../build/libs/JFDF.jar .

if [ ! -d "old" ]; then
  echo "Decompiling the original JFDF..."
  mkdir old
  fernflower JFDF-2.0.0b5-all.jar old
  unzip old/JFDF-2.0.0b5-all.jar -d old
  rm old/JFDF-2.0.0b5-all.jar
  rm old/javassist -r
  rm old/javax -r
  rm old/kotlin -r
  rm old/META-INF -r
  rm old/org -r
else
  echo "Old folder exists, delete it to re-decompile the original JFDF."
fi

rm new -r
mkdir new
fernflower JFDF.jar new
unzip new/JFDF.jar -d new
rm new/JFDF.jar
rm new/META-INF -r
rm diff.patch
diff new/net/jfdf old/net/jfdf -r > diff.patch