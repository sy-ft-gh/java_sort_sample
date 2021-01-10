package sort_algo;

public class Main {

	public static void main(String[] args) {
		var st = new Sorter<Integer>((x, y) -> Integer.compare(x, y));
		st.put(4).put(3).put(1).put(6).put(9).put(4).put(5).put(2).put(4).put(3);
		System.out.println(st);
		st.sort();
		System.out.println(st);
	}

}
