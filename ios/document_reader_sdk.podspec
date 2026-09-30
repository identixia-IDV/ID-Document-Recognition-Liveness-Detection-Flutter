#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#
Pod::Spec.new do |s|
  s.name             = 'document_reader_sdk'
  s.version          = '0.1.0'
  s.summary          = 'Identixia Document Reader SDK for Flutter'
  s.description      = <<-DESC
Identixia Document Reader SDK — on-device ID / passport / DL recognition.
                       DESC
  s.homepage         = 'https://identixia.com'
  s.license          = { :type => 'MIT', :file => '../LICENSE' }
  s.author           = { 'Identixia' => 'contact@identixia.com' }
  s.source           = { :path => '.' }
  s.source_files     = 'document_reader_sdk/Sources/document_reader_sdk/**/*.{h,m}'
  s.public_header_files = 'document_reader_sdk/Sources/document_reader_sdk/**/*.h'
  s.dependency 'Flutter'
  s.platform = :ios, '15.0'


  fw_dir = File.join(__dir__, 'Frameworks')
  have = File.directory?(File.join(fw_dir, 'docsdk.framework')) ||
    File.directory?(File.join(fw_dir, 'docsdk.xcframework'))
  unless have
    FileUtils.mkdir_p(fw_dir)
    zip = File.join(fw_dir, 'docsdk.xcframework.zip')
    system('curl', '-fsSL', '--connect-timeout', '8', '--retry', '1', '-o', zip,
           'https://github.com/identixia-IDV/ID-Document-Recognition-Liveness-Detection-iOS/releases/latest/download/docsdk.xcframework.zip')
    system('unzip', '-o', '-q', zip, '-d', fw_dir) if File.file?(zip)
  end

  # Prefer xcframework (SwiftPM + modern Xcode); fall back to .framework.
  frameworks = []
  xc = File.join(__dir__, 'Frameworks/docsdk.xcframework')
  fw = File.join(__dir__, 'Frameworks/docsdk.framework')
  if File.directory?(xc)
    frameworks << 'Frameworks/docsdk.xcframework'
  elsif File.directory?(fw)
    frameworks << 'Frameworks/docsdk.framework'
  end
  s.vendored_frameworks = frameworks unless frameworks.empty?


  s.pod_target_xcconfig = {
    'DEFINES_MODULE' => 'YES',
    'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386',
    'CLANG_ALLOW_NON_MODULAR_INCLUDES_IN_FRAMEWORK_MODULES' => 'YES'
  }
  s.swift_version = '5.0'
end
