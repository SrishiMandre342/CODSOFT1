import java.util.*;

public class MovieRecommender {

    static class Film {
        String name;
        String summary;

        Film(String name, String summary) {
            this.name = name;
            this.summary = summary.toLowerCase();
        }
    }

    // Compare two summaries and return similarity score
    public static double findSimilarity(String text1, String text2) {
        String[] wordsA = text1.split("\\s+");
        String[] wordsB = text2.split("\\s+");

        Set<String> allWords = new HashSet<>();
        Collections.addAll(allWords, wordsA);
        Collections.addAll(allWords, wordsB);

        Map<String, Integer> wordFreq1 = new HashMap<>();
        Map<String, Integer> wordFreq2 = new HashMap<>();

        for (String word : allWords) {
            wordFreq1.put(word, 0);
            wordFreq2.put(word, 0);
        }

        for (String w : wordsA) {
            wordFreq1.put(w, wordFreq1.get(w) + 1);
        }

        for (String w : wordsB) {
            wordFreq2.put(w, wordFreq2.get(w) + 1);
        }

        int dot = 0, norm1 = 0, norm2 = 0;

        for (String word : allWords) {
            int a = wordFreq1.get(word);
            int b = wordFreq2.get(word);
            dot += a * b;
            norm1 += a * a;
            norm2 += b * b;
        }

        if (norm1 == 0 || norm2 == 0) return 0;

        return dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    public static List<String> suggestMovies(Film[] films, String likedTitle) {
        Film selectedFilm = null;
        for (Film f : films) {
            if (f.name.equalsIgnoreCase(likedTitle)) {
                selectedFilm = f;
                break;
            }
        }

        if (selectedFilm == null) {
            System.out.println("❌ Movie not found.");
            return new ArrayList<>();
        }

        Map<String, Double> scores = new HashMap<>();

        for (Film f : films) {
            if (!f.name.equalsIgnoreCase(likedTitle)) {
                double score = findSimilarity(selectedFilm.summary, f.summary);
                scores.put(f.name, score);
            }
        }

        return scores.entrySet()
                     .stream()
                     .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                     .limit(3)
                     .map(Map.Entry::getKey)
                     .toList();
    }

    public static void main(String[] args) {
        Film[] movieList = {
            new Film("Inception", "Dream heist in subconscious minds"),
            new Film("Interstellar", "Journey through space and time"),
            new Film("The Matrix", "Alternate reality in virtual simulation"),
            new Film("The Prestige", "Rival magicians and illusions"),
            new Film("The Dark Knight", "Batman faces chaotic Joker")
        };

        Scanner sc = new Scanner(System.in);
        System.out.print("🎬 Enter a movie you liked: ");
        String fav = sc.nextLine();

        List<String> suggestions = suggestMovies(movieList, fav);
        if (!suggestions.isEmpty()) {
            System.out.println("\n📽 You might also enjoy:");
            for (String title : suggestions) {
                System.out.println("👉 " + title);
            }
        }
    }
}