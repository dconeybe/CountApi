cmake_minimum_required(VERSION 3.23)

project(CountApi C)

add_executable(
  CountApi
  CountApi.h
  CountApi.m
)

if(CMAKE_SYSTEM_NAME STREQUAL "Darwin")
  target_link_options(
    CountApi
    PUBLIC
      -framework Foundation
  )
else()
  # On non-macOS platforms, use GNUstep.
  # On Linux, this can be installed by running:
  # sudo apt-get install gnustep gnustep-devel

  execute_process(
    COMMAND gnustep-config --objc-flags
    OUTPUT_VARIABLE GNUSTEP_CONFIG_OBJC_FLAGS
    RESULT_VARIABLE GNUSTEP_CONFIG_OBJC_FLAGS_RESULT
  )
  if(NOT GNUSTEP_CONFIG_OBJC_FLAGS_RESULT EQUAL 0)
    message(FATAL_ERROR "Running gnustep-config --objc-flags failed")
  endif()
  string(STRIP "${GNUSTEP_CONFIG_OBJC_FLAGS}" GNUSTEP_CONFIG_OBJC_FLAGS_STRIPPED)
  string(REPLACE " " ";" GNUSTEP_CONFIG_OBJC_FLAGS_LIST "${GNUSTEP_CONFIG_OBJC_FLAGS_STRIPPED}")

  target_compile_options(
    CountApi
    PUBLIC
      ${GNUSTEP_CONFIG_OBJC_FLAGS_LIST}
  )

  execute_process(
    COMMAND gnustep-config --base-libs
    OUTPUT_VARIABLE GNUSTEP_CONFIG_BASE_LIBS
    RESULT_VARIABLE GNUSTEP_CONFIG_BASE_LIBS_RESULT
  )
  if(NOT GNUSTEP_CONFIG_BASE_LIBS_RESULT EQUAL 0)
    message(FATAL_ERROR "Running gnustep-config --base-libs failed")
  endif()
  string(STRIP "${GNUSTEP_CONFIG_BASE_LIBS}" GNUSTEP_CONFIG_BASE_LIBS_STRIPPED)
  string(REPLACE " " ";" GNUSTEP_CONFIG_BASE_LIBS_LIST "${GNUSTEP_CONFIG_BASE_LIBS_STRIPPED}")

  target_link_libraries(
    CountApi
    PUBLIC
      ${GNUSTEP_CONFIG_BASE_LIBS_LIST}
  )
endif()
