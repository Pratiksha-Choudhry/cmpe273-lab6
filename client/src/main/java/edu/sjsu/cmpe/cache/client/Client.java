package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        
        List<CacheServiceInterface> serverList = new ArrayList<CacheServiceInterface>();
        serverList.add(new DistributedCacheService("http://localhost:3000"));
        serverList.add(new DistributedCacheService("http://localhost:3001"));
        serverList.add(new DistributedCacheService("http://localhost:3002"));
        
        CharSequence val;
        int server;
        
        		System.out.println("PUT Key-Value pairs in CacheServer 0, 1, 2... ");
        
        		for(int key=1;key<=10;key++)
        		{
        			val = randomChar();
        			server = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key)), serverList.size());
        			serverList.get(server).put(key, val.toString());
        			System.out.println("put (key: "+key +"  value: "+ val+")" +" in CacheServer "+server);
        		}
        		
        		System.out.println("Retrieving all the Key-Value pairs form CacheServer... ");
        		
        		for(int key=1;key<=10;key++)
        		{
        			server = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key)), serverList.size());
        			System.out.println("get (key: "+key +"  value: "+ serverList.get(server).get(key) +")" + "from CacheServer " + server);
        		}

        System.out.println("Existing Cache Client...");
    }
    
    public static CharSequence randomChar(){
    	 		String str ="abcdefghijklmnopqrstuvwxyz";
    	 		int i = (int)(Math.random()*26);
    	 		CharSequence s = str.substring(i, i+1);
    	 		return s;
    	 	}

}
