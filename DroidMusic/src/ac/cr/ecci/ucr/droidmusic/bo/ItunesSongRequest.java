package ac.cr.ecci.ucr.droidmusic.bo;

public class ItunesSongRequest {
	int resultCount;
	public ItunesSongRequest(int resultCount, Song[] results) {
		this.resultCount = resultCount;
		this.results = results;
	}
	public int getResultCount() {
		return resultCount;
	}
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
	public Song[] getResults() {
		return results;
	}
	public void setResults(Song[] results) {
		this.results = results;
	}
	Song[] results;
	public Song getSong(int position){
		return results[position];		
	}
}
