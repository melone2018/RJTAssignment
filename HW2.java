package HW2;
import java.util.*;

public class HW2 {
	/**
	 * 
	 * @param str: a given string we need to reverse
	 * @return: a reversed version of the given string str
	 * 
	 * Step 1: initialize an empty string sb
	 * Step 2: iterate through the given String str from the end
	 * 		   append each character to sb
	 * Step 3: return sb
	 * 
	 * Dry run: str = "32abca"
	 * i	str[i]	 sb
	 * 5	  a		 a		 
	 * 4	  c      ac
	 * 3	  b	     acb 
	 * 2      a		 acba
	 * 1      2		 acba2
	 * 0      3		 acba23
	 */
	
	
	static String reverseString(String str) {
		StringBuilder sb = new StringBuilder();
		for(int i = str.length()-1; i >= 0; i--) {
			sb.append(str.charAt(i));
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param str: a given string, we need to find the repeated characters and their number of occurances
	 * @return  a collection tells us the repeated characters and their numbers of occurances. 
	 * 
	 * Step 1: initialize a map like structure to have the character in str as key and their number of occurances as value
	 * Step 2: iterate through the given String str from the beginning to the end to record the number of occurance of each character
	 * Step 3: Go through the map like structure, each the value of each key, if the value is 1, delete the entry from the map 
	 * Step 4: return the map like structure
	 * 
	 * Dry Run: str = "aabda3213"
	 * i	 str[i]     map
	 * 0	   a	    a -> 1
	 * 1	   a		a -> 2
	 * 2	   b		b -> b
	 * 3	   d	    d -> 1
	 * 4	   a		a -> 3
	 * 5	   3	    3 -> 1
	 * 6	   2		2 -> 1
	 * 7	   1		1 -> 1
	 * 8	   3		3 -> 2
	 * 
	 * map: {'a' -> 3, 'b' -> 1, 'd' -> 1, '1' -> 1, '2' -> 1, '3' -> 2}
	 * return map{'a'->3, '3' -> 2}
	 */
	static Map<Character, Integer> countChars(String str){
        Map<Character, Integer> hmap = new HashMap<Character, Integer>();
        for(int i = 0; i < str.length(); i++) {
            hmap.put(new Character(str.charAt(i)), hmap.getOrDefault(str.charAt(i), 0)+1);
        }
        //Iterator it = hmap.entrySet().iterator();
        Iterator<Integer> iterator = hmap.values().iterator();
        while(iterator.hasNext()) {
            if(iterator.next().intValue()==1) {
                iterator.remove();
            }
        }
        return hmap;
    }
	
	
	@SuppressWarnings("unchecked")
	static ArrayList<Interval> mergeIntervals(ArrayList<Interval> intervals) {
		Collections.sort(intervals);
		ArrayList<Interval> result = new ArrayList<Interval>();
		result.add(intervals.get(0));
		for(int i = 1; i < intervals.size(); i++) {
			Interval curEnd = result.get(result.size()-1);
			if(curEnd.getEnd() < intervals.get(i).getStart()) {
				result.add(intervals.get(i));
			} else {
				curEnd.setEnd(Math.max(curEnd.getEnd(), intervals.get(i).getEnd()));
			}
		}
		return result;
	}
	/**
	 * 
	 * @param nums: an unsorted array
	 * @return a 2 dimensional array with N x 3 dimension, each single array has 3 numbers in ascending order
	 * 
	 * Step 1: sort the given array nums, initialize a two dimensional array called res
	 * Step 2: for index i in range [0, nums.length - 3], do:
	 * 			while i equals 0 or array element at location i does not equal to element at location i-1, do:
	 * 				set temporary variable left to i+1;
	 * 				set temporary variable right to nums.length - 1
	 * 				while left < right, do:
	 * 					set temporary variable remain to nums[left] + nums[right]
	 * 					if remain equals to -1 * nums[i], do:
	 * 						add an array with elements nums[i], nums[left] and nums[right] to the result res;
	 * 						while left < right and nums[left] equals nums[right], do:
	 * 							increment left;
	 * 						end while
	 * 						while left < right and nums[right] equals nums[right-1], do:
	 * 							decrement right;
	 * 						end while
	 * 						increment left by 1;
	 * 						decrement right by 1;
	 * 					else if remain < (-1) * nums[i], increment left;
	 * 					else decrement right;
	 * 				end while
	 * 			end while
	 * 		  end for
	 * Step 3: return res;		
	 * 
	 * Dry Run: nums {-3,-2, -2, 5, 4}
	 *	Sort(nums): nums { -3, -2, -2, 4, 5}
	 * i = 0:
	 * 	left = i + 1 = 1, right = 4:
	 * 	remain = nums[1] + nums[4] = 3
	 *  remain == (-1) * (-3):
	 *  	res.add({-3, -2, 5})
	 *  	left -> 2 -> 3;
	 *  	right = 3
	 * i = 1：
	 * 	left = i + 1 = 2, right = 4;
	 * 	remain = nums[2] + nums[4] = 3
	 *  remain > (-1) * (-2):
	 *  	right = right - 1 = 3;
	 *  remain = nums[2] + nums[3] = 2
	 *  remain == (-1) * (-2):
	 *  	res.add({-2, -2, 4}); 
	 *  	left -> 3;
	 *  	right -> 2;
	 * i = 2:
	 * 	left = i + 1 = 3, right = 4;
	 * 	remain = nums[3] + nums[4] = 9;
	 * 	remain > (-1) * (-2)
	 * 		right -> 3;
	 * Return res as {{-3, -2, 5}, {-2, -2, 4}};
	 */
	static List<List<Integer>> findTriplets(int[] nums){
		Arrays.sort(nums);
		List<List<Integer>> res = new ArrayList<>();
		if(nums[0] > 0) return res;
		for(int i = 0; i < nums.length-2; i++) {
			if(i == 0 || (i > 0 && nums[i-1] != nums[i])) {
				int left = i + 1, right = nums.length-1;
				while(left < right) {
					int remain = nums[left] + nums[right];
					if(remain == (-1) * nums[i]) {
						res.add(Arrays.asList(nums[i], nums[left], nums[right]));
						while(left < right && nums[left] ==nums[right]) left++;
						while(left < right && nums[right] == nums[right-1]) right--;
						left++;
						right--;
					} else if(remain < (-1)*nums[i]) {
						left++;
					}else {
						right--;
					}
				}
			}
		}
		return res;
	}
	/**
	 * 
	 * @param nums: an array with two elements
	 * Step 1: set nums[0] to nums[0] + nums[1]
	 * Step 2: set nums[1] to nums[0] - nums[1]
	 * Step 3: set nums[0] to nums[0] - nums[1]
	 * 
	 * Dry run: nums: {5, -8}
	 * set nums[0] to 5-8 = -3
	 * set nums[1] to -3 - (-8) = 5
	 * set nums[0] to -3 - 5 = -8
	 * 
	 * nums: { -8, 5}
	 */
	static void swapWithoutThird(int[] nums) {
		nums[0] = nums[0] + nums[1];
		nums[1] = nums[0] - nums[1];
		nums[0] = nums[0] - nums[1];
	}
	
	static void permutationHelper(StringBuilder str, boolean[] visited, ArrayList<String> result, String constStr) {
		if(str.length() == constStr.length()) {
			result.add(str.toString());
			System.out.println(str.toString());
			return;
		} // abcc 
		// cacb
		for(int i = 0; i < constStr.length(); i++) {
			if(i > 0 && constStr.charAt(i-1)== constStr.charAt(i) && visited[i-1] == false) continue;
			if(visited[i]==false) {
				str.append(constStr.charAt(i));
				visited[i] = true;
				permutationHelper(str, visited, result, constStr);
				str.deleteCharAt(str.length()-1);
				visited[i] = false;
			}
		}
	}
	
	static ArrayList<String> getPermutation(String str){
		char[] tmpArray = str.toCharArray();
		Arrays.sort(tmpArray);
		String str2 = new String(tmpArray);
		ArrayList<String> result = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		boolean[] visited = new boolean[str.length()];
		permutationHelper(sb, visited, result, str2);
		return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		int[] nums = new int[2];
//		int[] nums2 = new int[] {-2, -3, 0, 1, 4, 5, -3, 6};
//		nums[0] = 9;
//		nums[1] = 4;
//		
//		HW2.swapWithoutThird(nums);
//		
//		List<List<Integer>> triplets = HW2.findTriplets(nums2);
//		System.out.println();
//		for(List<Integer> list : triplets) {
//			for(Integer i : list) {
//				System.out.print(i.intValue() + " ");
//			}
//			System.out.println();
//		}
//		
//		Map<Character, Integer> hmap = HW2.countChars("wo cao ni ma ma he ni ba ba");
//		//Iterator itmap = hmap.entrySet().iterator();
//		for(Map.Entry<Character, Integer> pair : hmap.entrySet()) {
//			System.out.println(pair.getKey().toString() + ": " + pair.getValue().intValue());
//		}
		ArrayList<Interval> arrayIntervals = new ArrayList<Interval>();
		arrayIntervals.add(new Interval(3, 4));
		arrayIntervals.add(new Interval(1, 4));
		arrayIntervals.add(new Interval(5, 8));
		arrayIntervals.add(new Interval(2, 7));
		//Collections.sort(arrayIntervals);
		
		
		ArrayList<Interval> res = HW2.mergeIntervals(arrayIntervals);
		for(Interval e : res) {
			System.out.println(e.getStart() + "; " + e.getEnd());
		}
		String s = "abac";
		ArrayList<String> result = HW2.getPermutation(s);
		
	}
}


