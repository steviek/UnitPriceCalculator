import com.google.common.collect.Range
import com.google.common.truth.DoubleSubject

fun DoubleSubject.isIn(range: ClosedRange<Double>) {
  isIn(Range.closed(range.start, range.endInclusive))
}