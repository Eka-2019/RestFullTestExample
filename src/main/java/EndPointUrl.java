

public enum EndPointUrl {
    APOD("planetary/apod"),
    NEOFEED("neo/rest/v1/feed"),
    NEOBROWSE("neo/rest/v1/neo/browse/"),
    NEOLOOKUP("neo/rest/v1/neo/"),
    EARTH("planetary/earth/imagery");



    String path;
    EndPointUrl(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }

    public String addPath(String additionalPath){
        return  path + additionalPath;
    }

}
