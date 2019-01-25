package org.lintx.plugins.modules.guiapi.page;

import java.util.List;

public interface PageInterface {
    String currentPageTitle(int page);
    String otherPageTitle(int page);
    List<String> currentPageLore(int page);
    List<String> otherPageLore(int page);
}
