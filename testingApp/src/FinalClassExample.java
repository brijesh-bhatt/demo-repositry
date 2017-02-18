import java.util.*;
import java.util.Iterator;

//Immutable Class Example
class Apple {
	private String color;
 
	public Apple(String color) {
		this.color = color;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		
		return this.color.hashCode();
	}
 
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if (!(obj instanceof Apple))
			return false;	
		if (obj == this)
			return true;
		return this.color.equals(((Apple) obj).color);
	}
 
	/*public static void main(String[] args) {
		Apple a1 = new Apple("green");
		Apple a2 = new Apple("red");
 
		//hashMap stores apple type and its quantity
		HashMap<Apple, Integer> m = new HashMap<Apple, Integer>();
		m.put(a1, 10);
		m.put(a2, 20);
		System.out.println(m.get(new Apple("green")));
	}*/
}



public final class FinalClassExample {

	private final int id;
	
	private final String name;
	
	private final HashMap<String,String> testMap;
	
	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public HashMap<String, String> getTestMap() {
		//return testMap;
		return (HashMap<String, String>) testMap.clone();
	}

	/**
	 * Constructor performing Deep Copy
	 * @param i
	 * @param n
	 * @param hm
	 */
	
	public FinalClassExample(int i, String n, HashMap<String,String> hm){
		System.out.println("Performing Deep Copy for Object initialization");
		this.id=i;
		this.name=n;
		HashMap<String,String> tempMap=new HashMap<String,String>();
		String key;
		Iterator<String> it = hm.keySet().iterator();
		while(it.hasNext()){
			key=it.next();
			tempMap.put(key, hm.get(key));
		}
		this.testMap=tempMap;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return false;
		
		FinalClassExample tempObj = (FinalClassExample) obj;
		Set<String> set = tempObj.testMap.keySet();
		
		if(this.id==tempObj.id && this.name.equals(tempObj.name) && this.testMap.keySet().containsAll(set))
			return true;
		else 
			return false;
		//return super.equals(obj);
	}
	
	
	/**
	 * Constructor performing Shallow Copy
	 * @param i
	 * @param n
	 * @param hm
	 */
	/**
	public FinalClassExample(int i, String n, HashMap<String,String> hm){
		System.out.println("Performing Shallow Copy for Object initialization");
		this.id=i;
		this.name=n;
		this.testMap=hm;
	}
	*/
	
	/**
	 * To test the consequences of Shallow Copy and how to avoid it with Deep Copy for creating immutable classes
	 * @param args
	 */
	public static void main(String[] args) {
		/*HashMap<String, String> h1 = new HashMap<String,String>();
		h1.put("1", "first");
		h1.put("2", "second");
		String s = "original";
		int i=10;
		FinalClassExample ce = new FinalClassExample(i,s,h1);
		h1.put("3","brijesh");
		FinalClassExample ce1 = new FinalClassExample(i,s,h1);
		
		System.out.println("Equality: " + ce.equals(ce1));
		
		//Lets see whether its copy by field or reference
		System.out.println(s==ce.getName());
		System.out.println(h1 == ce.getTestMap());
		System.out.println("ce id:"+ce.getId());
		System.out.println("ce name:"+ce.getName());
		System.out.println("ce testMap:"+ce.getTestMap());
		//change the hashmap variable values
		
		HashMap<String, String> hmTest = ce.getTestMap();
		hmTest.put("4", "new");
		
		System.out.println("ce testMap after changing variable from accessor methods:"+ce.getTestMap());*/
		Apple a1 = new Apple("green");
		Apple a2 = new Apple("red");
 
		//hashMap stores apple type and its quantity
		HashMap<Apple, Integer> m = new HashMap<Apple, Integer>();
		m.put(a1, 10);
		m.put(a2, 20);
		System.out.println(m.get(new Apple("green")));

	}

}