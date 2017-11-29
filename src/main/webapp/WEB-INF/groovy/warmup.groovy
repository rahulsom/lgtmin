import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

import java.util.concurrent.ScheduledThreadPoolExecutor

import static com.google.appengine.api.ThreadManager.backgroundThreadFactory

log.info "Warmup started"

def THREAD_POOL_EXECUTOR =
        new ScheduledThreadPoolExecutor(10, backgroundThreadFactory())

def gaeScheduler = Schedulers.from(THREAD_POOL_EXECUTOR)
RxJavaPlugins.onIoScheduler(gaeScheduler)
RxJavaPlugins.onComputationScheduler(gaeScheduler)
RxJavaPlugins.onNewThreadScheduler(gaeScheduler)

log.info "Warmup complete"
