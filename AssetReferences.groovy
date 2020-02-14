import com.day.cq.wcm.commons.ReferenceSearch;
import javax.jcr.NodeIterator;

def folderNode = session.getNode('/content/dam/site/en/folder1');
def childNodes = folderNode.getNodes();

ReferenceSearch referenceSearch = new ReferenceSearch();

childNodes.each { childNode ->
    def path = childNode.path;
    def results = referenceSearch.search(resourceResolver, path);
    println results;
    println results.size();
    
    def currentReference = false;
    if(results.size() == 0) {
        currentReference = true;
    } else if(results.size() == 1) {
        results.each { reference ->
            if(reference.toString().contains('/etc/groovyconsol')) {
                currentReference = true;
            } else {
                currentReference = false;
            }
        }
    }
    if(results.size() > 1 || !currentReference) {
        def node = session.getNode(path);
        node.remove();
        println 'removed asset : ' + path;
        //Uncomment the session.save() to remove the asset node.
        //session.save();
    }
}
