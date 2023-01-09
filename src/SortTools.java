package src;

import java.util.LinkedList;

public class SortTools {
    // sort a list of webpages in place using quicksort algorithm
    public static LinkedList<Webpage> quickSort(LinkedList<Webpage> list) {
        if (list.size() <= 1) {
            return list;
        }

        LinkedList<Webpage> left = new LinkedList<>();
        LinkedList<Webpage> right = new LinkedList<>();
        double pivotScore = round(list.get(0).getScore(), 3);
        String pivotTitle = list.get(0).getTitle();
        for (int i = 1; i < list.size(); i++) {
            double currentScore = round(list.get(i).getScore(), 3);
            String currentName = list.get(i).getTitle();
            if (currentScore > pivotScore || (currentScore == pivotScore && currentName.compareTo(pivotTitle) < 0)) {
                left.add(list.get(i));
            } else {
                right.add(list.get(i));
            }
        }

        left = quickSort(left);
        right = quickSort(right);
        left.add(list.get(0));
        left.addAll(right);
        return left;
    }

    // this helper method rounds a double value to a specified number of decimal places
    private static double round(double value, int decimalPlaces) {
        double factor = Math.pow(10, decimalPlaces);
        return Math.round(value * factor) / factor;
    }

}
