package lbd.bloomFilter.guavaVersion;

import java.nio.charset.Charset;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class GuavaBloomFilter001 {

	private final static String[] arr = new String[]{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
			"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
	
	public static void main(String[] args) {
		/**
		 * 创建了一个以string为key的bloom filter，预期的插入量是1KW，错误率是0.1%。
		 * 查询的时候，直接调 filter.mightContain()方法就可以，非常简单。
		 */
		BloomFilter<CharSequence> filter = BloomFilter.create(Funnels.stringFunnel( Charset.forName( "utf-8" ) ), 10000000, 0.001F);
		for (int i = 0; i < 9900000; i++) {
			filter.put( arr[ rand( 0, 35 ) ] + arr[ rand( 0, 35 ) ] + arr[ rand( 0, 35 ) ] + arr[ rand( 0, 35 ) ] );
		}
		long t1 = System.nanoTime();
		boolean mightContain = filter.mightContain( "hf6yy" );
		long t2 = System.nanoTime();
		System.out.println( "耗时：" + ( t2 - t1 ) + "纳秒" );
		System.out.println( "耗时：" + ( (double)( t2 - t1 ) / 1000000 ) + "毫秒" );
		System.out.println( mightContain );
	}
	
	/**
	 * 生成 “min <= 随机数 <= max ” 的随机数
	 * @return 
	 */
	public static int rand( int min, int max ){
		return min + (int)(Math.random() * (max-min+1));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/**
它会根据预期的插入量以及错误率，计算出numBits，即需要的位数。然后使用BitArray创建出numBits的数据位，BitArray的最大size=int.MAX_VALUE（约21亿）。
然后根据预期插入量以及计算出的numBits计算出最优的哈希函数个数。其实这里并不能说是最优，只是根据这两个参数做出的一种平衡。

这时候，千万别认为你可以将预期插入量设为21亿。事实上，你可以尝试一下，将预期插入量这个参数值从2亿慢慢增加至10亿，你会发现，内存占用都是一样的（试验前，请先调整JVM的-Xmx参数）。接下来我们看看在不同的参数条件下，numBits值以及numHashFunctions值的变化：

expectedInsertions	falsePositiveProbability	numBits	numHashFunctions
6KW	0.0001	1150207005	13
1亿	0.0001	1917011675	13
2亿	0.0001	2147483647	7
5亿	0.0001	2147483647	3 从上面的表格可以看到，在0.0001的错误率下，插入量不到1.5亿的时候，numBits已经到达了BitArray的最大容量了，这时如果再增加插入量，哈希函数个数就开始退化。
到5亿的时候，哈希函数个数退化到了只有3个，也就是说，对每一个key，只有3位来标识，这时准确率就会大大下降。

这时有两种解决方案：

第一种当然就是减少预期插入量，1亿以内，还是可以保证理论上的准确率的。

第二种，如果你的系统很大，就是会有上亿的key，这时可以考虑拆分，将一个大的bloom filter拆分成几十个小的（比如32或64个），每个最多可以容纳1亿，这时整体就能容纳32或64亿的key了。查询的时候，先对key计算一次哈希，然后取模，查找特定的bloom filter即可。
 */
}