package model;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code GameProblems} class reads known problems from a CSV file and provides a list of Problem objects.
 */
public class GameProblems {

    /** The list of problems. */
    private List<Problem> problems;

    /**
     * Constructs a {@code GameProblems} object and reads known problems from a CSV file.
     */
    public GameProblems() {
        String csvFile = "src/main/resources/known_problems.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> records = reader.readAll();

            problems = new ArrayList<>();

            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);
                int problemNumber = Integer.parseInt(record[0]);
                String codeSecret = record[3];

                List<Integer> validators = new ArrayList<>();
                for (int j = 4; j < record.length; j++) {
                    validators.add(Integer.parseInt(record[j]));
                }

                Problem problem = new Problem(problemNumber, codeSecret, validators);
                problems.add(problem);
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the list of known problems.
     *
     * @return The list of problems.
     */
    public List<Problem> getProblems() {
        return new ArrayList<>(problems);
    }
}
