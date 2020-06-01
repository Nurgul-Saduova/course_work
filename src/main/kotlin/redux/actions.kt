package redux

import data.Drug
import data.Review
import data.VisibilityFilter

class AddDrug(val drug: Drug):RAction
class AddReview(val review: Review):RAction
class sortByAscending():RAction
class sortByDescending():RAction
class SetVisibilityFilter(val filter: VisibilityFilter) : RAction