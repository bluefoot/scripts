package info.bluefoot.gunnarr.filteroptions;

/**
 * Options for filtering files in a DirOperation.
 */
public class FilterOptions {

    // ~ Instance variables =================================================

    private String wildCard;
    private final static String DEFAULT_WILDCARD = "*.*";

    // ~ Constructors =======================================================

    public FilterOptions() {
        this.wildCard = FilterOptions.DEFAULT_WILDCARD;
    }

    public FilterOptions(String wildCard) {
        if (wildCard == null) {
            this.wildCard = FilterOptions.DEFAULT_WILDCARD;
        } else {
            this.wildCard = wildCard;
        }
    }

    // ~ Getters/setters ====================================================

    public void setWildCard(String pattern) {
        this.wildCard = pattern;
    }

    public String getWildCard() {
        return this.wildCard;
    }

}
