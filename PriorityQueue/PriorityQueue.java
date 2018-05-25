package PriorityQueue;

import java.util.Arrays;

public class PriorityQueue<Key> {
	
	private Key[] pq;
	private int n;// number of items on priority queue

	/**
	 * Initializes an empty priority queue with the given initial capacity.
	 *
	 * @param initCapacity
	 *            the initial capacity of this priority queue
	 */
	@SuppressWarnings("unchecked")
	public PriorityQueue(int initCapacity) {
		pq = (Key[]) new Object[initCapacity + 1];
		n = 0;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public int size() {
		return n;
	}

	private void swim(int k) {
		while (k > 1 && less(k / 2, k)) {
			exch(k, k / 2);
			k = k / 2;
		}
	}

	private void sink(int k) {
		while (2 * k <= n) {
			int j = 2 * k;
			if (j < n && less(j, j + 1))
				j++;
			if (!less(k, j))
				break;
			exch(k, j);
			k = j;
		}
	}

	public void insert(Key x) {
		pq[n+1] = x;
		n++;
		swim(n);
	}

	public Key delMax() {
		Key root = pq[1];
		exch(1, n);
		n--;
		sink(1);
		return root;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean less(int i, int j) {
		return ((Comparable) pq[i]).compareTo(pq[j]) < 0;
	}

	private void exch(int i, int j) {
		Key swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}

	public static void main(String[] args) {
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(10);
		pq.insert(57);
		pq.insert(85);
		pq.insert(44);
		pq.insert(21);
		pq.insert(23);
		pq.insert(52);
		pq.insert(17);
		pq.insert(7);
		pq.insert(95);
		System.out.println(Arrays.toString(pq.pq));
		while (!pq.isEmpty()) {
			System.out.println(pq.delMax());
		}
	}

}
