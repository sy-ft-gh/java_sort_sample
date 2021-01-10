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
	 * �o�u���\�[�g
	 */
	private void bubble_sort() {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = items.size() - 1; j > i; j--) {
            	E item1 = items.get(j - 1);
            	E item2 = items.get(j);
                if (this.comp.compareTo(item1, item2) > 0) {
                    // ����ւ�
                	swap(j -1, j);
                }
             }
        }
	}
	/**
	 * ���ɂ�镪������
	 * @param begin �J�nindex
	 * @param end �I��index
	 * @param p �v�f��
	 * @return
	 */
	private int partition(int begin, int end, int p) {
	    var pivot = items.get(p);
	    var l = begin;
	    var r = end;
        // ��������������܂ŌJ��Ԃ�
        while (l <= r) {
          // ���v�f�ȏ�̃f�[�^������(�v�f����菬�����ꍇ�͐i�߂�)
          while (l <= end && this.comp.compareTo(this.get(l), pivot) < 0) {
        	  l++;
          }
          // ���v�f�����̃f�[�^������(�v�f���ȏ�̏ꍇ�͐i�߂�)
          while (r >= begin && this.comp.compareTo(this.get(r), pivot) >= 0) {
        	  r--;
          }
          if (l > r) {
            break;
          }
          // ����ւ�(����ւ��Ώۂ������n������A��u�ŃC���N�������g�{�f�N�������g)
          swap(l++, r--);
        }
        return l;
	}
	/**
	 * ���v�f�̑I��
	 * @param i �J�nindex
	 * @param j �I��index
	 * @return
	 */
	private int pivot(int i, int j) {
		var k = i + 1;
        // �����ɎQ�ƁA�ŏ��Ɍ��������قȂ�2�̗v�f�̂����A�傫�����̔ԍ���ԋp
		// (���l�̊ԃC���f�b�N�X��i�߂�)
		
		// �Q�lSRC��
        // while (k <= j && this.items.get(i).equals(this.items.get(k))) {
		// ��������
		int diff = 0;
        while (k <= j && (diff = this.comp.compareTo(this.items.get(i), this.items.get(k))) == 0) {
          k++;
        }
        // ������Ȃ�(�S�������v�f)�̏ꍇ�� -1��ԋp�B�����I��
        if (k > j) {
        	return -1;
        } 
        // �傫�����̃C���f�b�N�X��ԋp

        // �Q�lSRC��
        // return this.comp.compareTo(this.items.get(i), this.items.get(k)) >= 0 ? i : k;
        // ��������
        return diff >= 0 ? i : k;
	}
	/**
	 * Quick Sort Func (impl)
	 * @param begin start range index
	 * @param end end range index
	 */
	private void quick_sort(int begin, int end) {
		// ���v�f�̑I��
		int p = pivot(begin, end);
	    if (p != -1) {
	    	// ���������C���f�b�N�X�܂œ���ւ���
	        var partitionIndex = partition(begin, end, p);
	        // �p�[�e�B�V�����i���������C���f�b�N�X�𒆐S�Ƃ����Q�����j�̑O�����\�[�g
	        quick_sort(begin, partitionIndex-1);
	        // �p�[�e�B�V�����̌������\�[�g
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
	 * ������\��
	 */
	@Override
	public String toString() {
		return "Sorter [items=" + items + "]";
	}
}
