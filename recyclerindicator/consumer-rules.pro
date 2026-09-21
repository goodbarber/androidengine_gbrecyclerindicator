# Cell recycling relies on indicator class identity.
#
# GBBaseRecyclerAdapter.getGBRecycleViewIndicatorTypeView() decides whether two indicators may share
# a RecyclerView view type -- that is, whether the holder built by one may be rebound by the other --
# by comparing their runtime classes with getClass().equals(). R8's horizontal class merging folds
# same shaped sibling classes into a single runtime class, and that comparison then answers true for
# indicators that build completely unrelated cells: the adapter creates the holder from one of them
# and rebinds it with the other one's UIParameters, so the cast in the consuming cell throws
# ClassCastException. Measured once on a consumer app: 146 of its 236 indicator subclasses had been
# merged, 34 of them into one single class.
#
# The coupling lives in this library, so the rule ships with it instead of leaving every consumer to
# discover it through a crash. It only disables the merging optimization: allowshrinking keeps unused
# indicators removable and allowobfuscation keeps them renameable, so the Shrinking and Obfuscation
# scores Play Console reads from r8.json are untouched.
#
# Drop this rule only once getGBRecycleViewIndicatorTypeView() stops keying view types on Class
# identity.
-keep,allowshrinking,allowobfuscation class * extends com.goodbarber.recyclerindicator.GBRecyclerViewIndicator
