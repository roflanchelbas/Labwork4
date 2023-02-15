import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataCorrection {

    public static void main(String[] args) {
        String inputFile = "input.txt";
        String outputFile = "output.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");

                if (fields.length != 4) {
                    writer.write(line + "\n");
                    continue;
                }

                String firstName = fields[0];
                String lastName = fields[1];
                String age = fields[2];
                String phoneNumber = fields[3].replaceAll("\\s+", "");

                Pattern phonePattern = Pattern.compile("\\+\\d{1,3}\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}");
                Matcher phoneMatcher = phonePattern.matcher(phoneNumber);
                if (phoneMatcher.matches()) {
                    phoneNumber = phoneMatcher.group(0);
                } else {
                    phoneNumber = "";
                }

                String email = fields[3];
                Pattern emailPattern = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}\\b",
                        Pattern.CASE_INSENSITIVE);
                Matcher emailMatcher = emailPattern.matcher(email);
                if (emailMatcher.find()) {
                    email = emailMatcher.group(0);
                } else {
                    email = "";
                }

                String correctedLine = firstName + " " + lastName + "|" + age + "|" + phoneNumber + "|" + email;
                writer.write(correctedLine + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
