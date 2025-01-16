import org.json.JSONObject;
import java.io.FileReader;
import java.util.*;

public class ShamirSecretSharing {

    // Method to decode y-values based on their base
    private static int decodeBase(int base, String value) {
        return Integer.parseInt(value, base);
    }

    // Method to compute the constant term (c) using Lagrange interpolation
    private static double findConstantTerm(List<int[]> points) {
        double constantTerm = 0;

        for (int i = 0; i < points.size(); i++) {
            double term = points.get(i)[1];
            for (int j = 0; j < points.size(); j++) {
                if (i != j) {
                    term *= (0.0 - points.get(j)[0]) / (points.get(i)[0] - points.get(j)[0]);
                }
            }
            constantTerm += term;
        }

        return constantTerm;
    }

    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("testcases.json");
            char[] buffer = new char[1024];
            int length = reader.read(buffer);
            String jsonString = new String(buffer, 0, length);
            System.out.println("Read JSON content: " + jsonString);
            JSONObject data = new JSONObject(jsonString);
            JSONObject keys = data.getJSONObject("keys");
            int n1 = keys.getInt("n");
            int k1 = keys.getInt("k");
            List<int[]> points1 = new ArrayList<>();
            JSONObject case1 = data.getJSONObject("1");
            for (String key : case1.keySet()) {
                int x = Integer.parseInt(key);
                JSONObject point = case1.getJSONObject(key);
                int y = decodeBase(point.getInt("base"), point.getString("value"));
                points1.add(new int[]{x, y});
            }
            List<int[]> points2 = new ArrayList<>();
            JSONObject case2 = data.getJSONObject("2");
            for (String key : case2.keySet()) {
                int x = Integer.parseInt(key);
                JSONObject point = case2.getJSONObject(key);
                int y = decodeBase(point.getInt("base"), point.getString("value"));
                points2.add(new int[]{x, y});
            }
            double secret1 = findConstantTerm(points1.subList(0, k1));
            double secret2 = findConstantTerm(points2.subList(0, k1));

            System.out.printf("Secret Code for Test Case 1: %.0f\n", secret1);
            System.out.printf("Secret Code for Test Case 2: %.0f\n", secret2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
