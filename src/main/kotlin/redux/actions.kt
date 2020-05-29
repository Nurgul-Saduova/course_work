package redux

import data.VisibilityFilter

class AddDrug():RAction
class AddReview(val index:Int):RAction
class sortByAscending():RAction
class sortByDescending():RAction
class SetVisibilityFilter(val filter: VisibilityFilter) : RAction