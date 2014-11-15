package ca.ualberta.cs.cmput301f14t14.questionapp.data;

public class SearchHit<T> {
	private String _index;
	private String _type;
	private String _id;
	private String _version;
	private boolean found;
	private T _source;

	public SearchHit() {

	}

	public String get_index() {
		return _index;
	}

	public void set_index(String _index) {
		this._index = _index;
	}

	public String get_type() {
		return _type;
	}

	public void set_type(String _type) {
		this._type = _type;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_version() {
		return _version;
	}

	public void set_version(String _version) {
		this._version = _version;
	}

	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	public T getSource() {
		return _source;
	}

	public void setSource(T source) {
		this._source = source;
	}

	@Override
	public String toString() {
		return "SimpleElasticSearchResponse [_index=" + _index + ", _type="
				+ _type + ", _id=" + _id + ", _version=" + _version
				+ ", found=" + found + ", _source=" + _source + "]";
	}
	
	
}
