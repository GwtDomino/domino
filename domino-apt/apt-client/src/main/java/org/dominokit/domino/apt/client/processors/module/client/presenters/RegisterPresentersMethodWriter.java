package org.dominokit.domino.apt.client.processors.module.client.presenters;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.dominokit.domino.api.client.mvp.PresenterRegistry;
import org.dominokit.domino.api.client.mvp.presenter.Presentable;
import org.dominokit.domino.api.client.mvp.presenter.PrototypePresenter;
import org.dominokit.domino.api.client.mvp.presenter.SingletonPresenter;
import org.dominokit.domino.apt.commons.AbstractRegisterMethodWriter;

import javax.lang.model.element.Modifier;

public class RegisterPresentersMethodWriter extends AbstractRegisterMethodWriter<PresenterEntry, String> {

    public RegisterPresentersMethodWriter(TypeSpec.Builder clientModuleTypeBuilder) {
        super(clientModuleTypeBuilder);
    }

    @Override
    protected String methodName() {
        return "registerPresenters";
    }

    @Override
    protected Class<?> registryClass() {
        return PresenterRegistry.class;
    }

    @Override
    protected void registerItem(PresenterEntry entry, MethodSpec.Builder methodBuilder) {
        MethodSpec makeMethod = MethodSpec.methodBuilder("make")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(Presentable.class)
                .addStatement("return new $T()", ClassName.bestGuess(entry.name))
                .build();
        TypeSpec lazyLoaderType = TypeSpec.anonymousClassBuilder("$T.class.getCanonicalName(), $T.class.getCanonicalName()"
                , ClassName.bestGuess(entry.name), ClassName.bestGuess(entry.name))
                .superclass(entry.singleton ? SingletonPresenter.class : PrototypePresenter.class)
                .addMethod(makeMethod)
                .build();
        methodBuilder.addStatement("registry.registerPresenter($L)", lazyLoaderType);
    }

    @Override
    protected PresenterEntry parseEntry(String presenter) {
        String[] requestPair = presenter.split(":");
        return new PresenterEntry(requestPair[0], Boolean.valueOf(requestPair[1]));
    }

}
