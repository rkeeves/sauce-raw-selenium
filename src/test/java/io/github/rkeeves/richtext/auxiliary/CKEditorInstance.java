package io.github.rkeeves.richtext.auxiliary;

import lombok.NonNull;

public interface CKEditorInstance {

    CKEditorInstance toggleBold();

    CKEditorInstance toggleUnderline();

    String getData();

    CKEditorInstance dataMustBe(@NonNull String expected);

    CKEditorInstance insertText(@NonNull String text);
}
