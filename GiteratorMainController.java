package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import views.html.*;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.PagedSearchIterable;
import play.mvc.*;
import play.mvc.Controller;
import play.mvc.Result;

import models.Repository;

import com.google.inject.Inject;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;

public class GiteratorMainController extends Controller {
    @Inject
    FormFactory formFactory;

    public Result index() {
        return ok("Giterator index");
    }

    public Result displaySearchPage() throws IOException { //@TODO: Add an array list of strings as arguement
        PagedSearchIterable<GHRepository> searchIterable = Repository.getAllRepos("octokit");
        Iterator<GHRepository> iterator = searchIterable.iterator();

        ArrayList<String> repo_list = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> desc = new ArrayList<>();
        ArrayList<String> language = new ArrayList<>();
        ArrayList<String> homepage = new ArrayList<>();


        String str = "";
        while (iterator.hasNext()) {
            GHRepository repo = iterator.next();
            str += repo.getName() + " " + repo.getOwnerName() + " " + repo.getDescription() + " " + repo.getLanguage() + " " + repo.getHomepage() + "\n";
            name.add(repo.getName());
            desc.add(repo.getDescription());
            language.add(repo.getLanguage());
            homepage.add(repo.getHomepage());


            repo_list.add(str);
        }
        return ok(views.html.display.render(name,desc,language,homepage, repo_list));
    }

    public Result displayTopic(String topic) {
        return ok("Giterator explore topic: " + topic);
    }

    public Result displaySearchResults() {
        return ok("Giterator search results");
    }

    public Result displaySearch() {
        //display search page with empty array list
        return ok(views.html.search.render(new ArrayList<String>()));
    }

    public Result process(Http.Request request) {
        DynamicForm requestData = formFactory.form().bindFromRequest(request);
        String[] allTopics = requestData.get("topics").split(" ");

        ArrayList<String> repoList = new ArrayList<>();

        for (String topic : allTopics) {
            Set<Repository> repos = Repository.getByTopic("octokit", topic);
            for (Repository repo : repos) {
                String str = repo.repoName + " " + repo.userName + " " + repo.topic1 + " " + repo.topic2 + " " + repo.topic3 + "\n";
                repoList.add(str);
            }
        }

        return ok(views.html.search.render(repoList));
    }

}
