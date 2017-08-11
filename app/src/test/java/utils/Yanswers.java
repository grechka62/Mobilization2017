package utils;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import java.util.WeakHashMap;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subjects.PublishSubject;
public class Yanswers {
    public static final Answer NEVER_RX = invocation -> {
        if (returns(invocation, Observable.class)) {
            return Observable.never();
        }
        if (returns(invocation, Single.class)) {
            return Single.create(emitter -> {});
        }
        if (returns(invocation, Completable.class)) {
            return Completable.never();
        }
        
        if (returns(invocation, Flowable.class)) {
            return Flowable.never();
        }
        return null;
    };
    public static final Answer EMPTY_RX = invocation -> {
        if (returns(invocation, Observable.class)) {
            return Observable.empty();
        }
        if (returns(invocation, Single.class)) {
            return Single.just(null);
        }
        if (returns(invocation, Completable.class)) {
            return Completable.complete();
        }
        
        if (returns(invocation, Flowable.class)) {
            return Flowable.empty();
        }
        return null;
    };
    public static final Answer FIRST_ARGUMENT = invocation -> invocation.getArguments()[0];
    public static final Answer SUBJECT_RX = new Answer() {
        private final WeakHashMap<InvocationOnMock, Object> cachedSubjects = new WeakHashMap<>();
        @Override
        public Object answer(final InvocationOnMock invocation) throws Throwable {
            if (returns(invocation, Observable.class)) {
                if (cachedSubjects.containsKey(invocation)) {
                    return cachedSubjects.get(invocation);
                }
                final PublishSubject<Object> subject = PublishSubject.create();
                cachedSubjects.put(invocation, subject);
                return subject;
            }
            
            if (returns(invocation, Flowable.class)) {
                if (cachedSubjects.containsKey(invocation)) {
                    return cachedSubjects.get(invocation);
                }
                PublishProcessor<Object> subject = PublishProcessor.create();
                cachedSubjects.put(invocation, subject);
                return subject;
            }
            return null;
        }
    };
    private static boolean returns(InvocationOnMock invocation, Class<?> clazz) {
        return invocation.getMethod().getReturnType().isAssignableFrom(clazz);
    }
}