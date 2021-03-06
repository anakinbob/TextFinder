import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Helper class for a list of result objects
 */
public class ResultList {
    private final List<Result> _resultList;
    private static final double DISREGARD_DISTANCE = 0.5;
    public ResultList() {
        _resultList = new ArrayList<>();
    }
    public ResultList(ResultList toAdd) {
        _resultList = new ArrayList<>();
        if(toAdd != null) {
            _resultList.addAll(toAdd._resultList);
        }
    }
    public void addResult(Result result) {
        _resultList.add(result);
    }
    public void addEntryToAllResults(TextLocation textLocation) {
        for(Result result : _resultList) {
            result.addResultPart(textLocation);
        }
    }
    public List<Result> getResultList() {
        _resultList.sort(Comparator.naturalOrder());
        cleanList();
        return _resultList;
    }

    public static ResultList mergeAll(List<ResultList> allResultLists) {
        if(allResultLists == null || allResultLists.size() == 0) {
            return null;
        }
        ResultList toReturn = new ResultList();
        for(ResultList resultList : allResultLists) {
            toReturn._resultList.addAll(resultList._resultList);
        }
        return toReturn;
    }

    /**
     * Assumes ordered
     */
    private void cleanList() {
        if(_resultList.size() == 0) {
            return;
        }
        TextLocation val = _resultList.get(0).getIndex();
        for(int i = 1; i < _resultList.size();) {

            if((_resultList.get(i).getIndex().getAbsoluteLocation() - val.getAbsoluteLocation()) < DISREGARD_DISTANCE) {
                _resultList.remove(i);
            } else {
                val = _resultList.get(i).getIndex();
                i++;
            }
        }
    }
}
