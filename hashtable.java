class HashTable{
	/*
	Hashtable impelemntation uses  hashtable with a chained data structure
	new hashes are appended to the list.
	*/
	private Hash hashTable[];

	public HashTable(){
		/*Create new hash table*/
		this.hashTable =  new Hash [13];

		/*Initiate Hash  Tables */
		for ( int i=0; i< hashTable.length; i++)
			this.hashTable[i] = new Hash();

	}

	private long hash(String firstName, String lastName){
		/* Function to generate hashes for entires based on name of entrant. 
		@param: First and  last name strings
		@returns: Hash as a string
		*/
		long hash =0;
		int g = 31;
		String name = firstName + lastName;
		for( int i =0 ; i< name.length(); i++ ){
			/* Mod Long.Max_Val compresses hash space reducing long overflows*/
			hash =  ((hash *g) + name.charAt(i)) % Long.MAX_VALUE;
		}
		return hash;
	}

	private int hashBucket(long hash){
		/* Function to return  compessed hash bucket based on entry hash.
		@param: Long with the hash for an entry
		@returns: Hash Bucket Entry
		*/
		System.out.println(hash);
		/* Using Absolute value allows ignoring of negative hash values*/
		return (int) ((hash  % 13) <= 0 ? (0-hash%13): (hash%13));
	}


	public boolean insert(Hash h){
	/* Function to add hash to hash table. Apppends new hashes to end of bucket list
	@param: Hash to be inserted into the hastable
	@returns: boolean value if the insertion was sucessfull
	*/
	
		/*Bucket index provides the index for the set of nodes the new node will be attached do
		the code is broken out for clarity*/
		int bucketIndex = this.hashBucket( h.getHash());
		/* Links the bucket list together by appending the new hashes to the last node in the bucket.*/
		/* Insert Debug */
		System.out.println(bucketIndex);
		System.out.println(h.getHash());
		h.print();
		this.hashTable[bucketIndex].print();
		this.hashTable[ bucketIndex ].returnLastNode().setNext(h);
		/* Insert Debug 2*/
		this.hashTable[ bucketIndex ].returnLastNode().print();

	return true;
	}


	public boolean delete(Hash h){
	/* Function to delete hashes form hash table.
	Uses java reference passing ot manipulate hash bucket using alias checkHash.
	@param: Hashes to be deleted from the has table
	@returns: The values that were deleted from the has table.
	*/
		Hash checkHash, prevHash;
		h.print();
		System.out.println(h.getHash());
		System.out.println(this.hashBucket( h.getHash()) );

		int bucketIndex = this.hashBucket( h.getHash());
		
		
		prevHash  = hashTable[ bucketIndex];
		checkHash = prevHash.getNext();

		while( prevHash.getHash() != h.getHash() && checkHash.getHash() != h.getHash()  && checkHash.getNext() != null ){
			prevHash = checkHash;
			checkHash = checkHash.getNext();
		}

		if(checkHash.getHash() == h.getHash())
			prevHash = checkHash;

		//Case 1: Hash is the root of the hash bucket
		if(prevHash.getHash() == h.getHash())
			prevHash = checkHash;
		//Case 2: Hash is not the root of the bucket
		if(checkHash.getHash() == h.getHash())
			prevHash.setNext( checkHash.getNext());
		//Case 3: No hash found then nothing to do.
		if( checkHash == checkHash.returnLastNode())
			return false;

		//Hash was found and function didn't end early
		return true;
			
		


	}



	public Hash get(Hash h){
	/* Function to return the data elements form the hashes provided
	@param: Hashes to mapped to values
	@returns: The values that correspond to the given hashes
	*/
		Hash checkHash;
		
		int bucketIndex = this.hashBucket( h.getHash());
		return this.lookUp( h.getHash(), bucketIndex);

	/*
		checkHash = hashTable[ bucketIndex];
		while( checkHash.getHash() != h.getHash() && checkHash.getNext() != null){
			checkHash = checkHash.getNext();
		}
		if(checkHash.getHash() == h.getHash() ){
			checkHash.print();
			return checkHash.getNext();
		}
		
		else
			return null;
			*/
	}

	private Hash lookUp(long hashVal, int bucketIndex) {
		Hash checkHash;
		checkHash = hashTable[ bucketIndex];
		while( checkHash.getHash() != hashVal && checkHash.getNext() != null){
			checkHash = checkHash.getNext();
		}
		if(checkHash.getHash() == hashVal ){
			//checkHash.print();
			return checkHash;
		}
		
		else
			return null;

	}
	

	public Hash nameLookUp(String firstName, String lastName){
		long hash = this.hash(firstName, lastName);
		return lookUp( hash , this.hashBucket(hash));

	}

	public void nameDelete(String firstName, String lastName){
		long hash =  this.hash(firstName, lastName);

		this.delete( this.lookUp( hash , this.hashBucket(hash))  );
	}

	public Hash add(String firstName, String lastName,  String phone, String email){
		Hash h = new Hash( this.hash(firstName, lastName), firstName, lastName, phone, email);
		this.insert(h);

		return h;
	}

}