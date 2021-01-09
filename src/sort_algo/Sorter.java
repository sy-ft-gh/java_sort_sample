package sort_algo;

import java.util.ArrayList;
import java.util.List;

public class Sorter<E> {
	private List<E> items;
	private SortMethods method;
	private SortCompare<E> comp;
	private boolean autoSort;
	
	public Sorter(SortCompare<E> comp) {
		this(SortMethods.SORT_QUICK, comp, false);
	}
	public Sorter(SortMethods method, SortCompare<E> comp) {
		this(method, comp, false);
	}
	public Sorter(SortMethods method, SortCompare<E> comp, boolean autoSort) {
		this.items = new ArrayList<E>();
		this.method = method;
		this.comp = comp;
		this.autoSort = autoSort;
	}
	
	private void swap(int i, int j) {
		var item1 = this.items.get(i);
		var item2 = this.items.get(j);
    	items.set(i, item2);
    	items.set(j, item1);
	}
	
	private void bubble_sort() {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = items.size() - 1; j > i; j--) {
            	E item1 = items.get(j - 1);
            	E item2 = items.get(j);
                if (this.comp.compareTo(item1, item2) > 0) {
                    // 入れ替え
                	swap(j -1, j);
                }
             }
        }
	}
	private int partition(int begin, int end, int p) {
	    var pivot = items.get(p);
	    var l = begin;
	    var r = end;
        // 検索が交差するまで繰り返し
        while (l <= r) {
          // 軸要素以上のデータを検索
          while (l <= end && this.comp.compareTo(this.get(l), pivot) < 0) {
        	  l++;
          }
          // 軸要素未満のデータを検索
          while (r >= begin && this.comp.compareTo(this.get(r), pivot) >= 0) {
        	  r--;
          }
          if (l > r) {
            break;
          }
          swap(l++, r--);
        }
        return l;
	}	
	private int pivot(int i, int j) {
		var k = i + 1;
        //順に参照、最初に見つかった異なる2つの要素のうち、大きい方の番号を返却
        while (k <= j && this.items.get(i).equals(this.items.get(k))) {
          k++;
        }
        // 全部同じ要素の場合は -1を返却。即時終了
        if (k > j) {
          return -1;
        }
        if (this.comp.compareTo(this.items.get(i), this.items.get(k)) >= 0) {
          return i;
        }
        return k;
		
	}
	private void quick_sort(int begin, int end) {
		int p = pivot(begin, end);
	    if (p != -1) {
	        var partitionIndex = partition(begin, end, p);

	        quick_sort(begin, partitionIndex-1);
	        quick_sort(partitionIndex, end);
	    }
	}
	private void quick_sort() {
		quick_sort(0, this.items.size() - 1);
	}
	public void sort() {
		sort(this.method);
	}
	public void sort(SortMethods method) {
		switch (method) {
			case SORT_BUBBLE:
				bubble_sort();
				break;
			case SORT_QUICK:
				quick_sort();
				break;
			default:
				throw new IllegalArgumentException("Method is Nothing: " + method);
		}
	}
	
	public Sorter<E> put(E item) {
		this.items.add(item);
		if (this.autoSort) sort();
		return this;
	}
	
	public E get(int i) {
		return this.items.get(i);
	}
	@Override
	public String toString() {
		return "Sorter [items=" + items + "]";
	}
}
