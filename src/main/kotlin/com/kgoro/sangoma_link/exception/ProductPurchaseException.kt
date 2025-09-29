

import lombok.Data
import lombok.EqualsAndHashCode

@EqualsAndHashCode(callSuper = true)
@Data
class ProductPurchaseException(msg: String): Throwable()