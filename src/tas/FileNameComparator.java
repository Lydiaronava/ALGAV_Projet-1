package tas;
import java.io.File;
import java.util.Comparator;

public class FileNameComparator implements Comparator<File> {
	    public int compare( File a, File b ) {
	        return a.getName().compareTo( b.getName() );
	    }
	}