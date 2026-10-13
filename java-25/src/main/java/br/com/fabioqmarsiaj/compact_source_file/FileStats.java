void main(String[] args) throws Exception {
    if(args.length != 1) {
        IO.println("Usage: FileStats.java sample.txt");
    }

    var path = Path.of(args[0]);
    var content = Files.readString(path);

    IO.println("File name: " + path.getFileName());
    IO.println("File size: " + Files.size(path) + " bytes");
    IO.println("File lines: " + content.lines().count());
    IO.println("File words: " + content.split("\\s").length);
}