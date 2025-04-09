package racoony.software.klubi.domain

import arrow.core.Either

interface Success
interface Failure

typealias Result<A, B> = Either<A, B>

