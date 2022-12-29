package browser.rx.client.impl;

import browser.rx.TOPIC;
import browser.rx.client.InnerMqClient;
import browser.rx.client.SubscribeCallback;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class InnerMqClientImpl implements InnerMqClient {

    public Map<TOPIC, PublishSubject<Object>> subjects = new HashMap<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final String id;
    private boolean destroyed = false;

    public InnerMqClientImpl(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public PublishSubject<Object> sub(TOPIC topic, SubscribeCallback callback) {
        PublishSubject<Object> subject = subjects.computeIfAbsent(topic, k -> PublishSubject.create());
        this.compositeDisposable.add(subject.subscribe(callback::exec));
        return subject;
    }

    @Override
    public void pub(TOPIC topic, Object msg) {
        if (this.destroyed) {
            return;
        }
        PublishSubject<Object> subject = subjects.get(topic);
        if (subject != null) {
            subject.onNext(msg);
        }
    }

    @Override
    public void destroy() {
        this.destroyed = true;
        this.subjects.clear();
        if (!this.compositeDisposable.isDisposed()) {
            this.compositeDisposable.dispose();
        }
    }

}
