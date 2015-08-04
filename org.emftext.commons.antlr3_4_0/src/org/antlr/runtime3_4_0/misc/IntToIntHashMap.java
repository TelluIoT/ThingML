package org.antlr.runtime3_4_0.misc;

/**
 * This class provides a minimal implementation of a HashMap that uses primitive
 * integers both as keys and as values.
 */
public class IntToIntHashMap {
	
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	public static int collisions = 0;

	/**
	 * This array holds both the keys (at the even indexes) and the values (at
	 * the odd ones). 
	 */
	private int hashTable[];
	
	/**
	 * The number of entries that have been stored in this map.
	 */
	private int entries = 0;
	
	/**
	 * The maximum load factor. If the number of entries exceeds this factor,
	 * the map is automatically expanded.
	 */
	private double loadFactor;
	
	/**
	 * We can't store the value for key 0 in the hashTable, because
	 * zero is used to indicate that a bucket is empty.
	 */
	private int valueForZero;
	
	/**
	 * The logical size of the map; this is half the physical size.
	 */
	private int logicalHashTableSize;
	
	/**
	 * The threshold where to expand the map. This equals the current logical
	 * size of the map times the load factor.
	 */
	private int loadThreshold;
	
	/**
	 * This value is returned when calling {@link #get(int)} for a key that is
	 * not contained in this map. The normal Java HashMap implementation returns
	 * <code>null</code> in such cases, but since we use primitive integers we 
	 * can't return <code>null</code>. The default value for this is zero.
	 */
	private int valueForMissingEntries;
	
	public IntToIntHashMap() {
		this(DEFAULT_INITIAL_CAPACITY, 0.5);
	}

	public IntToIntHashMap(int initialCapacity, double loadFactor) {
		super();
		this.loadFactor = loadFactor;
		createHashTable(initialCapacity);
	}

	public void put(int key, int value) {
		if (key == 0) {
			valueForZero = value;
			return;
		}
		if (entries >= loadThreshold) {
			// we need to expand the table
			expandHashTable();
		}
		
		putInternal(key, value);
	}

	private void putInternal(int key, int value) {
		int index = index(key);
		int nextIndex = index; 
		while (true) {
			// check whether the bucket is empty
			int physicalIndex = nextIndex * 2;
			if (hashTable[physicalIndex] == 0) {
				// store key and value
				hashTable[physicalIndex] = key;
				hashTable[physicalIndex + 1] = value;
				entries++;
				return;
			}
			collisions++;
			// try the next bucket (this may be caused by hash
			// collisions)
			nextIndex = (nextIndex + 1) % logicalHashTableSize;
			// stop if we have ran through the whole table
			if (nextIndex == index) {
				throw new RuntimeException("All buckets in hash table are full.");
			}
		}
	}

	private void createHashTable(int logicalCapacity) {
		// we need to double the size, because we need one field for
		// the key and a second one to store the value
		hashTable = new int[logicalCapacity * 2];
		this.logicalHashTableSize = logicalCapacity;
		this.loadThreshold = (int) (logicalHashTableSize * loadFactor);
	}

	private void expandHashTable() {
		int newSize = hashTable.length;
		// double the size of the table (use current physical size as new 
		// logical size, which is effectively twice as much)
		resizeHashTable(newSize);
	}

	private void shrinkHashTable() {
		int newSize = logicalHashTableSize;
		// shrink to a size where all entries fit in w.r.t. the load factor
		while (entries < (newSize / 2) * loadFactor && newSize != DEFAULT_INITIAL_CAPACITY) {
			newSize = Math.max(DEFAULT_INITIAL_CAPACITY, newSize / 2);
		}
		resizeHashTable(newSize);
	}

	private void resizeHashTable(int newLogicalCapacity) {
		if (newLogicalCapacity == logicalHashTableSize) {
			return;
		}
		int[] currentTable = hashTable;
		createHashTable(newLogicalCapacity);
		// move all entries to the new table
		for (int i = 0; i < currentTable.length; i = i + 2) {
			int key = currentTable[i];
			if (key == 0) {
				// empty bucket, skip this one
				continue;
			}
			int value = currentTable[i + 1];
			putInternal(key, value);
			// we must not count this put since the entry was contained in the
			// map before
			this.entries--;
		}
	}

	public int get(int key) {
		if (key == 0) {
			return valueForZero;
		}
		int index = index(key);
		int nextIndex = index; 
		while (true) {
			// check whether the key matches
			int physicalIndex = nextIndex * 2;
			if (hashTable[physicalIndex] == key) {
				return hashTable[physicalIndex + 1];
			}
			if (hashTable[physicalIndex] == 0) {
				// found empty bucket, key is not contained in this map
				return valueForMissingEntries;
			}
			// found a filled bucket that contained the wrong key.
			// try the next bucket (this situation is caused by hash collisions).
			nextIndex = (nextIndex + 1) % logicalHashTableSize;
			// stop if we have ran through the whole table
			if (nextIndex == index) {
				break;
			}
		}
		return valueForMissingEntries;
	}

	private int index(int key) {
		// TODO use another hash function?
		return key % this.logicalHashTableSize;
	}

	/**
	 * Returns the current size of this map (i.e., the number of contained
	 * entries). This excludes the key/value pair where the key is zero.
	 */
	public int size() {
		return this.entries;
	}

	/**
	 * Sets the value that is returned for missing keys.
	 * 
	 * TODO this should only be used at initialization
	 */
	public void setValueForMissingEntries(int valueForMissingEntries) {
		this.valueForMissingEntries = valueForMissingEntries;
		this.valueForZero = valueForMissingEntries;
	}

	public void removeEntries(int maxKeyValue) {
		int[] currentTable = hashTable;
		createHashTable(logicalHashTableSize);
		for (int i = 0; i < currentTable.length; i = i + 2) {
			int key = currentTable[i];
			if (key == 0) {
				// found empty bucket, can continue
				continue;
			}
			if (key > maxKeyValue) {
				int value = currentTable[i + 1];
				putInternal(key, value);
			}
			entries--;
		}
		if (maxKeyValue > 0) {
			this.valueForZero = valueForMissingEntries;
		}
		// shrink table if required
		int lowerBound = (int) ((logicalHashTableSize / 2) * loadFactor);
		if (logicalHashTableSize != DEFAULT_INITIAL_CAPACITY && entries < lowerBound) {
			shrinkHashTable();
		}
	}
}
