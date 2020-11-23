package spotify.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import spotify.api.interfaces.BrowseApi;
import spotify.exceptions.HttpRequestFailedException;
import spotify.factories.RetrofitHttpServiceFactory;
import spotify.models.albums.AlbumSimplifiedPaging;
import spotify.models.categories.CategoryFull;
import spotify.models.categories.CategoryFullPaging;
import spotify.models.playlists.FeaturedPlaylistCollection;
import spotify.models.playlists.PlaylistSimplifiedPaging;
import spotify.models.recommendations.RecommendationCollection;
import spotify.retrofit.services.BrowseService;
import spotify.utils.LoggingUtil;
import spotify.utils.ResponseChecker;
import spotify.utils.ValidatorUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BrowseApiRetrofit implements BrowseApi {
    private final Logger logger = LoggerFactory.getLogger(UserApiRetrofit.class);
    private final String accessToken;
    private final BrowseService browseService;

    public BrowseApiRetrofit(String accessToken) {
        this.accessToken = accessToken;
        this.browseService = RetrofitHttpServiceFactory.getBrowseService();
    }

    @Override
    public CategoryFull getCategory(String categoryId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch category.");
        Call<CategoryFull> httpCall = browseService.getCategory("Bearer " + this.accessToken, categoryId, options);

        try {
            logger.info("Executing HTTP call to fetch category.");
            logger.debug(String.format("Fetching category %s with following values: %s.", categoryId, options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<CategoryFull> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Category has been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch category has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public PlaylistSimplifiedPaging getCategoryPlaylists(String categoryId, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch category playlists.");
        Call<PlaylistSimplifiedPaging> httpCall = browseService.getCategoryPlaylists("Bearer " + this.accessToken, categoryId, options);

        try {
            logger.info("Executing HTTP call to fetch category playlists.");
            logger.debug(String.format("Fetching category %s playlists with following values: %s.", categoryId, options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<PlaylistSimplifiedPaging> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Category playlists have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch category playlists has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public CategoryFullPaging getCategories(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch categories.");
        Call<CategoryFullPaging> httpCall = browseService.getCategories("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch categories.");
            logger.debug(String.format("Fetching categories with following values: %s.", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<CategoryFullPaging> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Categories have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch categories has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public FeaturedPlaylistCollection getFeaturedPlaylists(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch featured playlists.");
        Call<FeaturedPlaylistCollection> httpCall = browseService.getFeaturedPlaylists("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch featured playlists.");
            logger.debug(String.format("Fetching featured playlists with following values: %s", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<FeaturedPlaylistCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Featured playlists have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch featured playlists has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public AlbumSimplifiedPaging getNewReleases(Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        logger.trace("Constructing HTTP call to fetch new releases.");
        Call<AlbumSimplifiedPaging> httpCall = browseService.getNewReleases("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch new releases.");
            logger.debug(String.format("Fetching new releases with following values: %s", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<AlbumSimplifiedPaging> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("New releases have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch new releases has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    @Override
    public RecommendationCollection getRecommendations(List<String> listOfSeedArtists, List<String> listOfSeedGenres, List<String> listOfSeedTracks, Map<String, String> options) {
        options = ValidatorUtil.optionsValueCheck(options);

        mapSeedParameters(listOfSeedArtists, listOfSeedGenres, listOfSeedTracks, options);

        logger.trace("Constructing HTTP call to fetch recommendations.");
        Call<RecommendationCollection> httpCall = browseService.getRecommendations("Bearer " + this.accessToken, options);

        try {
            logger.info("Executing HTTP call to fetch recommendations.");
            logger.debug(String.format("Fetching recommendations with following values: %s", options));
            LoggingUtil.logHttpCall(logger, httpCall);
            Response<RecommendationCollection> response = httpCall.execute();

            ResponseChecker.throwIfRequestHasNotBeenFulfilledCorrectly(response.errorBody());

            logger.info("Recommendations have been successfully fetched.");
            return response.body();
        } catch (IOException ex) {
            logger.error("HTTP request to fetch recommendations has failed.");
            throw new HttpRequestFailedException(ex.getMessage());
        }
    }

    private void mapSeedParameters(List<String> listOfSeedArtists, List<String> listOfSeedGenres, List<String> listOfSeedTracks, Map<String, String> options) {
        final String artistSeedIds = String.join(",", listOfSeedArtists);
        final String genreSeedIds = String.join(",", listOfSeedGenres);
        final String trackSeedIds = String.join(",", listOfSeedTracks);

        if (!artistSeedIds.isEmpty()) {
            options.put("seed_artists", artistSeedIds);
        }

        if (!genreSeedIds.isEmpty()) {
            options.put("seed_genres", genreSeedIds);
        }

        if (!trackSeedIds.isEmpty()) {
            options.put("seed_tracks", trackSeedIds);
        }
    }
}
