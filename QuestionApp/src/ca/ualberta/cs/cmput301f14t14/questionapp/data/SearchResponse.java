/* This file was written by Victor Guana
 * See https://github.com/guana/elasticsearch
 * 
 * Used with permission
 */
package ca.ualberta.cs.cmput301f14t14.questionapp.data;


public class SearchResponse<T> {

	private int took;
	private boolean timed_out;
	private Shard _shards;
	private Hits<T> hits;
	
	public SearchResponse() {}

	public int getTook() {
		return took;
	}

	public void setTook(int took) {
		this.took = took;
	}

	public boolean isTimed_out() {
		return timed_out;
	}

	public void setTimed_out(boolean timed_out) {
		this.timed_out = timed_out;
	}

	public Shard get_shards() {
		return _shards;
	}

	public void set_shards(Shard _shards) {
		this._shards = _shards;
	}

	public Hits<T> getHits() {
		return hits;
	}

	public void setHits(Hits<T> hits) {
		this.hits = hits;
	}	   
}

	

class Shard {
	private int total;
	private int successful;
	private int failed;
	
	public Shard() {}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getSuccessful() {
		return successful;
	}
	public void setSuccessful(int successful) {
		this.successful = successful;
	}
	public int getFailed() {
		return failed;
	}
	public void setFailed(int failed) {
		this.failed = failed;
	}
}