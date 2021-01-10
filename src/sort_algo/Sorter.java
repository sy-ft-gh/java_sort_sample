package sort_algo;

import java.util.ArrayList;
import java.util.List;

/*
 * https://moneyforward.com/engineers_blog/2016/02/02/sort-algorithm/
 * */
public class Sorter<E> {
	private List<E> items;
	private SortMethods method;
	private SortCompare<E> comp;
	private boolean autoSort;
	
	/**
	 * constructor
	 * @param comp Compare Method
	 */
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
	/**
	 * swap items
	 * @param i swap index1
	 * @param j swap index2
	 */
	private void swap(int i, int j) {
		var item1 = this.items.get(i);
		var item2 = this.items.get(j);
    	items.set(i, item2);
    	items.set(j, item1);
	}
	/**
	 * バブルソート
	 */
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
	/**
	 * 軸による分割処理
	 * @param begin 開始index
	 * @param end 終了index
	 * @param p 要素軸
	 * @return
	 */
	private int partition(int begin, int end, int p) {
	    var pivot = items.get(p);
	    var l = begin;
	    var r = end;
        // 検索が交差するまで繰り返し
        while (l <= r) {
          // 軸要素以上のデータを検索(要素軸より小さい場合は進める)
          while (l <= end && this.comp.compareTo(this.get(l), pivot) < 0) {
        	  l++;
          }
          // 軸要素未満のデータを検索(要素軸以上の場合は進める)
          while (r >= begin && this.comp.compareTo(this.get(r), pivot) >= 0) {
        	  r--;
          }
          if (l > r) {
            break;
          }
          // 入れ替え(入れ替え対象を引き渡した後、後置でインクリメント＋デクリメント)
          swap(l++, r--);
        }
        return l;
	}
	/**
	 * 軸要素の選択
	 * @param i 開始index
	 * @param j 終了index
	 * @return
	 */
	private int pivot(int i, int j) {
		var k = i + 1;
        // 昇順に参照、最初に見つかった異なる2つの要素のうち、大きい方の番号を返却
		// (同値の間インデックスを進める)
		
		// 参考SRC版
        // while (k <= j && this.items.get(i).equals(this.items.get(k))) {
		// 高速化版
		int diff = 0;
        while (k <= j && (diff = this.comp.compareTo(this.items.get(i), this.items.get(k))) == 0) {
          k++;
        }
        // 見つからない(全部同じ要素)の場合は -1を返却。即時終了
        if (k > j) {
        	return -1;
        } 
        // 大きい方のインデックスを返却

        // 参考SRC版
        // return this.comp.compareTo(this.items.get(i), this.items.get(k)) >= 0 ? i : k;
        // 高速化版
        return diff >= 0 ? i : k;
	}
	/**
	 * Quick Sort Func (impl)
	 * @param begin start range index
	 * @param end end range index
	 */
	private void quick_sort(int begin, int end) {
		// 軸要素の選択
		int p = pivot(begin, end);
	    if (p != -1) {
	    	// 交差したインデックスまで入れ替える
	        var partitionIndex = partition(begin, end, p);
	        // パーティション（交差したインデックスを中心とした２分割）の前半をソート
	        quick_sort(begin, partitionIndex-1);
	        // パーティションの公判をソート
	        quick_sort(partitionIndex, end);
	    }
	}
	/**
	 * Quick Sort func
	 */
	private void quick_sort() {
		quick_sort(0, this.items.size() - 1);
	}
	/**
	 * Execute Sort @ Selected Method
	 */
	public void sort() {
		sort(this.method);
	}
	/**
	 * Execute Sort
	 * @param method Sort Method
	 */
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
	/**
	 * setter
	 * @param item put it on tail
	 * @return this
	 */
	public Sorter<E> put(E item) {
		this.items.add(item);
		if (this.autoSort) sort();
		return this;
	}
	
	/**
	 * gtter
	 * @param i index
	 * @return Element
	 */
	public E get(int i) {
		return this.items.get(i);
	}
	/**
	 * 文字列表現
	 */
	@Override
	public String toString() {
		return "Sorter [items=" + items + "]";
	}
}
