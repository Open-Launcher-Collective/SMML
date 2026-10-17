package dev.cadindie;

import me.judge.nativebind.GenerateJNIWrapper;
import me.judge.nativebind.GenerateNativeWrapper;

public enum DependencyType {
    @GenerateNativeWrapper(prefix = "smmml", name = "dependencyTypeEmbedded")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "dependencyTypeEmbedded")
    EMBEDDED,
    @GenerateNativeWrapper(prefix = "smmml", name = "dependencyTypeOptional")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "dependencyTypeOptional")
    OPTIONAL,
    @GenerateNativeWrapper(prefix = "smmml", name = "dependencyTypeRequired")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "dependencyTypeRequired")
    REQUIRED,
    @GenerateNativeWrapper(prefix = "smmml", name = "dependencyTypeTool")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "dependencyTypeTool")
    TOOL,
    @GenerateNativeWrapper(prefix = "smmml", name = "dependencyTypeIncompatible")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "dependencyTypeIncompatible")
    INCOMPATIBLE,
    @GenerateNativeWrapper(prefix = "smmml", name = "dependencyTypeIncluded")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "dependencyTypeIncluded")
    INCLUDED,
    @GenerateNativeWrapper(prefix = "smmml", name = "dependencyTypeUnknown")
    @GenerateJNIWrapper(jniClass = "dev.cadindie.SMMLJNIBridge", name = "dependencyTypeUnknown")
    UNKNOWN
}
