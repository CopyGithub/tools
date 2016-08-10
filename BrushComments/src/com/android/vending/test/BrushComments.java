package com.android.vending.test;

import java.util.ArrayList;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

/**
 * 在google play上顶踩指定程序的用户的评论
 * <p>
 * 需要传入3个参数<br>
 * 1.指定程序的包名<br>
 * 2.指定程序在搜索后显示的程序名(只需要一部分,以便进行区分搜索结果)<br>
 * 3.查看用户的数量<br>
 * 三者以{@code \,}分割, 参数key为params
 * 
 * @author chchen
 *
 */
public class BrushComments extends UiAutomatorTestCase {
    // 使用的外部文字内容
    private static final String FIVE_STAR = "5.0";
    private static final String THREE_STAR = "3.0";
    private static final String TWO_STAR = "2.0";
    private static final String ONE_STAR = "1.0";
    private static final String PACKAGE_NAME = "com.android.vending";

    private final Utils javaUtils = new Utils();
    private final UiSelector selector = new UiSelector();
    private ArrayList<Reviewer> reviewers = new ArrayList<Reviewer>();
    private ArrayList<String> accountNames = new ArrayList<String>();
    private String packageName = "";
    private String searchKey = "";
    private int num = 0;

    private class Reviewer {
        private String review_author = "";
        private String review_rating = "";
        private String review_date = "";
        private String review_metadata = "";
        private UiObject action_image;
        private String review_title = "";
        private String review_text = "";
    }

    public void testBrushComments() throws Exception {
        String params = getParams().getString("params");
        String[] args = params.split("\\,");
        for (String arg : args) {
            System.out.println("arg: " + arg);
        }
        packageName = args[0];
        searchKey = args[1].replace("0", " ");
        num = Integer.valueOf(args[2]);
        startApp();
        getAccounts();
        for (String accountName : accountNames) {
            reviewers.clear();
            changeAccount(accountName);
            enterAllReview(packageName, searchKey);
            modifyReviewSort(1);// 最新的顺序
            searchReviewer();
            writeReviewer(accountName);
        }
    }

    private void writeReviewer(String accountName) {
        String filePath = "/mnt/sdcard/aa/" + accountName + ".txt";
        System.out.println(filePath);
        javaUtils.createFileOrDir(filePath, false, true);
        for (Reviewer reviewer : reviewers) {
            javaUtils.writeText(reviewer.review_author, filePath, true);
            javaUtils.writeText("\t", filePath, true);
            javaUtils.writeText(reviewer.review_rating, filePath, true);
            javaUtils.writeText("\t", filePath, true);
            javaUtils.writeText(reviewer.review_text, filePath, true);
            javaUtils.writeText("\r\n", filePath, true);
        }
    }

    private void changeAccount(String accountName) throws Exception {
        if (!enterAccountListInterface()) {
            return;
        }
        if (accountName.equals(accountNames.get(0))) {
            UiDevice.getInstance().pressBack();
        } else {
            UiObject account = new UiObject(selector.text(accountName));
            while (!account.exists()) {
                swipeHalf();
                javaUtils.sleep(1000);
            }
            javaUtils.sleep(1000);
            account.click();
            javaUtils.sleep(1000);
        }
    }

    private void getAccounts() throws Exception {
        if (!enterAccountListInterface()) {
            return;
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 20; j++) {
                UiObject account = new UiObject(
                        selector.resourceId("com.android.vending:id/account_name").instance(j));
                if (account.exists()) {
                    String accountName = account.getText();
                    if (!accountNames.contains(accountName)) {
                        accountNames.add(accountName);
                    }
                } else {
                    break;
                }
            }
            swipeHalf();
        }
        UiDevice.getInstance().pressBack();
    }

    private boolean enterAccountListInterface() throws Exception {
        UiObject menu = new UiObject(
                selector.resourceId("com.android.vending:id/navigation_button"));
        UiObject googlePlay = new UiObject(
                selector.resourceId("com.android.vending:id/search_box_idle_text"));
        long endTime = System.currentTimeMillis() + 20 * 1000;
        while (endTime > System.currentTimeMillis() && !googlePlay.exists()) {
            UiDevice.getInstance().pressBack();
            javaUtils.sleep(1000);
        }
        javaUtils.sleep(1000);
        menu.click();
        UiObject accountList = new UiObject(
                selector.resourceId("com.android.vending:id/toggle_account_list_button"));
        javaUtils.sleep(2 * 1000);
        if (accountList.exists()) {
            javaUtils.sleep(1000);
            accountList.click();
            javaUtils.sleep(1000);
            return true;
        } else {
            UiObject accountName = new UiObject(
                    selector.resourceId("com.android.vending:id/account_name"));
            if (!accountName.exists()) {
                accountName = new UiObject(
                        selector.resourceId("com.android.vending:id/display_name"));
            }
            accountNames.add(accountName.getText());
            UiDevice.getInstance().pressBack();
            return false;
        }
    }

    private void swipeHalf() {
        swipe(0.5f, 0.7f, 0.5f, 0.3f);
    }

    private void swipe(float startX, float startY, float endX, float endY) {
        int height = UiDevice.getInstance().getDisplayHeight();
        int wide = UiDevice.getInstance().getDisplayWidth();
        UiDevice.getInstance().swipe((int) (wide * startX), (int) (height * startY),
                (int) (wide * endX), (int) (height * endY), 30);
    }

    private void startApp() throws Exception {
        javaUtils.runtimeExec(null, "am start -W " + PACKAGE_NAME, 15);
        System.out.println("am start -W " + PACKAGE_NAME);
        javaUtils.sleep(1000);
    }

    private void enterAllReview(String searchName, String resultKey) throws Exception {
        UiObject mainContent = new UiObject(
                selector.resourceId("com.android.vending:id/bucket_items"));
        waitForExists(mainContent);
        javaUtils.sleep(1000);
        UiObject search = new UiObject(
                selector.resourceId("com.android.vending:id/search_box_idle_text"));
        waitForExists(search);
        javaUtils.sleep(1000);
        search.click();
        javaUtils.sleep(2 * 1000);
        UiObject input = new UiObject(
                selector.resourceId("com.android.vending:id/search_box_text_input"));
        input.setText(searchName);
        javaUtils.sleep(2 * 1000);
        UiDevice.getInstance().pressEnter();
        UiObject more = new UiObject(selector.resourceId("com.android.vending:id/header_more"));
        if (waitForExists(more)) {
            javaUtils.sleep(1000);
            more.click();
        }
        UiObject dolphin = new UiObject(selector.textContains(resultKey));
        waitForExists(dolphin);
        javaUtils.sleep(1000);
        dolphin.click();
        javaUtils.sleep(5 * 1000);
        while (true) {
            swipeHalf();
            javaUtils.sleep(1000);
            UiObject allReviews = new UiObject(
                    selector.resourceId("com.android.vending:id/reviews_statistics_panel"));
            if (allReviews.exists()) {
                allReviews.click();
                break;
            }
        }
    }

    private void modifyReviewSort(int index) throws Exception {
        UiObject sortBox = new UiObject(
                selector.resourceId("com.android.vending:id/reviews_sort_box"));
        waitForExists(sortBox);
        javaUtils.sleep(1000);
        sortBox.clickAndWaitForNewWindow();
        javaUtils.sleep(1000);
        UiObject sortReview = new UiObject(
                selector.className("android.widget.CheckedTextView").index(index));
        sortReview.click();
        javaUtils.sleep(1000);
    }

    /**
     * 收集评论的用户信息, 并针对低分和高分进行顶踩操作
     * 
     * @throws UiObjectNotFoundException
     */
    private void searchReviewer() throws UiObjectNotFoundException {
        System.out.println("cishu:" + num);
        int reviewerNum = 0;
        while (reviewerNum < num) {
            boolean swipe = false;
            for (int i = 0; i < 10; i++) {
                swipe = false;
                Reviewer reviewer = new Reviewer();
                UiObject review_author = new UiObject(
                        selector.resourceId("com.android.vending:id/review_author").instance(i));
                if (review_author.exists()) {
                    reviewer.review_author = review_author.getText();
                } else {
                    break;
                }
                if (getReviewRating(reviewer, review_author)) {
                    review_author = getAuthor(reviewer.review_author);
                    swipe = true;
                }
                if (getReviewText(reviewer, review_author)) {
                    review_author = getAuthor(reviewer.review_author);
                    swipe = true;
                }
                if (getActionImage(reviewer, review_author)) {
                    swipe = true;
                }
                if (!addReviewer(reviewer)) {
                    executeBrushComment(reviewer);
                    reviewerNum++;
                }
                System.out.println("num :" + reviewerNum + "author:" + reviewer.review_author);
                if (reviewerNum >= num) {
                    break;
                }
                if (swipe) {
                    i = 0;
                }
            }
            if (!swipe) {
                swipeHalf();
            }
        }
    }

    private boolean addReviewer(Reviewer reviewer) {
        boolean same = false;
        for (Reviewer originReviewer : reviewers) {
            if (originReviewer.review_author.equals(reviewer.review_author)) {
                if (originReviewer.review_text.equals(reviewer.review_text)) {
                    same = true;
                }
            }
        }
        if (!same) {
            reviewers.add(reviewer);
        }
        return same;
    }

    private UiObject getAuthor(String author) {
        UiObject review_author = new UiObject(
                selector.text(author).resourceId("com.android.vending:id/review_author"));
        return review_author;
    }

    private boolean getActionImage(Reviewer reviewer, UiObject review_author)
            throws UiObjectNotFoundException {
        String author = review_author.getText();
        boolean swipe = false;
        for (int i = 0; i < 10; i++) {
            UiObject action_image = new UiObject(
                    selector.resourceId("com.android.vending:id/action_image").instance(i));
            if (action_image.exists()) {
                if (action_image.getBounds().top == review_author.getBounds().top) {
                    reviewer.action_image = action_image;
                    return swipe;
                }
            } else {
                if (!swipe) {
                    swipeHalf();
                    review_author = getAuthor(author);
                    swipe = true;
                    i = 0;
                } else {
                    break;
                }
            }
        }
        reviewer.action_image = null;
        return swipe;
    }

    private boolean getReviewRating(Reviewer reviewer, UiObject review_author)
            throws UiObjectNotFoundException {
        boolean swipe = false;
        for (int i = 0; i < 10; i++) {
            UiObject review_rating = new UiObject(
                    selector.resourceId("com.android.vending:id/review_rating").instance(i));
            if (review_rating.exists()) {
                if (review_rating.getBounds().top > review_author.getBounds().bottom) {
                    reviewer.review_rating = review_rating.getContentDescription();
                    return swipe;
                }
            } else {
                if (!swipe) {
                    swipeHalf();
                    review_author = getAuthor(reviewer.review_author);
                    swipe = true;
                    i = 0;
                } else {
                    break;
                }
            }
        }
        reviewer.review_rating = "";
        return swipe;
    }

    private boolean getReviewText(Reviewer reviewer, UiObject review_author)
            throws UiObjectNotFoundException {
        String author = review_author.getText();
        boolean swipe = false;
        for (int i = 0; i < 10; i++) {
            UiObject review_text = new UiObject(
                    selector.resourceId("com.android.vending:id/review_text").instance(i));
            if (review_text.exists()) {
                if (review_text.getBounds().top > review_author.getBounds().bottom) {
                    reviewer.review_text = review_text.getText();
                    return swipe;
                }
            } else {
                if (!swipe) {
                    swipeHalf();
                    review_author = getAuthor(author);
                    swipe = true;
                    i = 0;
                } else {
                    break;
                }
            }
        }
        reviewer.review_text = "";
        return swipe;
    }

    private void executeBrushComment(Reviewer reviewer) throws UiObjectNotFoundException {
        if (reviewer.review_rating.contains(FIVE_STAR)) {
            clickAction(reviewer.action_image, 0);
        } else if (reviewer.review_rating.contains(THREE_STAR)
                || reviewer.review_rating.contains(TWO_STAR)
                || reviewer.review_rating.contains(ONE_STAR)) {
            clickAction(reviewer.action_image, 1);
        }

        if (FIVE_STAR.equals(reviewer.review_rating)) {
            clickAction(reviewer.action_image, 0);
        } else if (THREE_STAR.equals(reviewer.review_rating)
                || TWO_STAR.equals(reviewer.review_rating)
                || ONE_STAR.equals(reviewer.review_rating)) {
            clickAction(reviewer.action_image, 1);
        }
    }

    private void clickAction(UiObject action, int index) throws UiObjectNotFoundException {
        action.clickAndWaitForNewWindow();
        UiObject unHelpful = new UiObject(
                selector.className("android.widget.CheckedTextView").index(index));
        unHelpful.click();
        javaUtils.sleep(1000);
        UiObject ok = new UiObject(
                selector.className("android.widget.Button").resourceId("android:id/button1"));
        ok.click();
        javaUtils.sleep(1000);
    }

    private boolean waitForExists(UiObject uiObject) throws Exception {
        long endTime = System.currentTimeMillis() + 20 * 1000;
        while (endTime > System.currentTimeMillis()) {
            if (uiObject.exists()) {
                return true;
            }
            javaUtils.sleep(1000);
        }
        return false;
    }
}
