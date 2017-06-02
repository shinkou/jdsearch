package com.github.shinkou.jdsearch;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

public class Starter
{
	private static void keys(Jedis j, String pattern)
	{
		Set<String> ss = j.keys(pattern);
		ss.forEach(s -> System.out.println(s));
	}

	private static void scan(Jedis j, int c, String pattern)
	{
		ScanParams sprms = new ScanParams().count(c).match(pattern);
		String cursor = ScanParams.SCAN_POINTER_START;
		ScanResult<String> srs = null;
		boolean done = false;

		while(! done)
		{
			srs = j.scan(cursor, sprms);
			cursor = srs.getStringCursor();
//			System.out.println("CURSOR: " + cursor);
			List<String> rs = srs.getResult();
			rs.forEach(s -> System.out.println(s));
			done = "0".equals(cursor);
		}
	}

	public static void main(String[] args)
	{
		String method = System.getProperty("method", "scan");
		String host = System.getProperty("host", "localhost");
		int port = Integer.parseInt(System.getProperty("port", "6379"));

		Jedis j = new Jedis(host, port);

		if (method.equals("keys"))
		{
			Arrays.stream(args).forEach(s -> keys(j, s));
		}
		else if (method.equals("scan"))
		{
			int c = Integer.parseInt
			(
				System.getProperty("scan.count", "1024")
			);

			Arrays.stream(args).forEach(s -> scan(j, c, s));
		}
		else
		{
			System.err.println("Unknown method.");
		}

		j.close();
	}
}
