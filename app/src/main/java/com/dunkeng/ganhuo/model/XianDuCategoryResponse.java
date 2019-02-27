package com.dunkeng.ganhuo.model;

import java.util.List;

/**
 * Created by Damon.Han on 2019/2/27 0027.
 *
 * @author Damon
 */
public class XianDuCategoryResponse {

    /**
     * error : false
     * results : [{"_id":"57c83777421aa97cbd81c74d","en_name":"wow","name":"科技资讯","rank":1},{"_id":"57c83577421aa97cb162d8b1","en_name":"apps","name":"趣味软件/游戏","rank":5},{"_id":"57c83627421aa97cbd81c74b","en_name":"imrich","name":"装备党","rank":50},{"_id":"57c836b4421aa97cbd81c74c","en_name":"funny","name":"草根新闻","rank":100},{"_id":"5827dc81421aa911e32d87cc","en_name":"android","name":"Android","rank":300},{"_id":"582c5346421aa95002741a8e","en_name":"diediedie","name":"创业新闻","rank":340},{"_id":"5829c2bc421aa911e32d87e7","en_name":"thinking","name":"独立思想","rank":400},{"_id":"5827dd7b421aa911d3bb7eca","en_name":"iOS","name":"iOS","rank":500},{"_id":"5829b881421aa911dbc9156b","en_name":"teamblog","name":"团队博客","rank":600}]
     */

    private boolean error;
    private List<XianDuCategory> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<XianDuCategory> getResults() {
        return results;
    }

    public void setResults(List<XianDuCategory> results) {
        this.results = results;
    }


}
