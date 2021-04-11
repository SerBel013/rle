import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import java.util.ArrayList;
import java.util.List;
public class RecoderLauncher {

    @Option(name = "-u", usage = "unpacking file")
    private boolean unpack = false;

    @Option(name = "-z", usage = "packing file")
    private boolean pack = false;

    @Option(name = "-out", usage = "output to this file")
    private String out;

    @Argument
    private List<String> arg = new ArrayList<String>();

    public static void main(String[] args) {
        new RecoderLauncher().launch(args);
    }

    public void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if (arg.isEmpty() || (!pack && !unpack) || (!arg.get(0).equals("pack-rle") || arg.size() != 2)) {
                System.err.println("Error: invalid arguments entered");
                System.err.println("Example: pack-rle [-z|-u] [-out outputname.txt] inputname.txt");
                throw new IllegalArgumentException("");
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("pack-rle [-z|-u] [-outputname.txt] inputname.txt");
            parser.printUsage(System.err);
        }
        String text = arg.get(1);
        RleKt.rle(pack, text, out);
    }
}