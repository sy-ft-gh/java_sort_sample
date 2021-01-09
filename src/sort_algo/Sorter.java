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
	
	private void bubble_sort() {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = items.size() - 1; j > i; j--) {
            	E item1 = items.get(j - 1);
            	E item2 = items.get(j);
                if (this.comp.compareTo(item1, item2) > 0) {
                    // “ü‚ê‘Ö‚¦
                	items.set(j -1, item2);
                	items.set(j,  item1);
                }
             }
        }
	}
	private int partition(int begin, int end) {
	    var pivot = items.get(end);
	    int i = (begin-1);

	    for (int j = begin; j < end; j++) {
	    	E item2 = items.get(j);
	        if (comp.compareTo(item2, pivot) <= 0) {
	            i++;
	    	    E item1 = items.get(i);
	    	    items.set(i, item2);
	    	    items.set(j, item1);
	        }
	    }
	    E item1 = items.get(i + 1);
	    items.set(i + 1, items.get(end));
	    items.set(end, item1);

	    return i + 1;
	}	
	private void quick_sort(int begin, int end) {
	    if (begin < end) {
	        int partitionIndex = partition(begin, end);

	        quick_sort(begin, partitionIndex-1);
	        quick_sort(partitionIndex+1, end);
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
